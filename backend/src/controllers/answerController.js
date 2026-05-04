import { Anthropic } from '@anthropic-ai/sdk';
import sharp from 'sharp';

const client = new Anthropic();

export const processAnswer = async (req, res) => {
  try {
    const { problemStatement, studentAnswerImage, studentAnswerText } = req.body;

    if (!problemStatement) {
      return res.status(400).json({ error: 'Problem statement is required' });
    }

    // Prepare the message for Claude
    let messageContent = [];

    // Add problem context
    messageContent.push({
      type: 'text',
      text: `You are an expert teacher assistant. Analyze the following problem and compare the student's answer with what the correct answer should be.

PROBLEM STATEMENT:
${problemStatement}`
    });

    // Add student answer (image or text)
    if (studentAnswerImage) {
      messageContent.push({
        type: 'image',
        source: {
          type: 'base64',
          media_type: 'image/jpeg',
          data: studentAnswerImage
        }
      });
      messageContent.push({
        type: 'text',
        text: 'This is the student\'s handwritten/scanned answer. Please:'
      });
    } else if (studentAnswerText) {
      messageContent.push({
        type: 'text',
        text: `Student's answer:\n${studentAnswerText}\n\nPlease:`
      });
    } else {
      return res.status(400).json({ error: 'Either studentAnswerImage or studentAnswerText is required' });
    }

    messageContent.push({
      type: 'text',
      text: `1. Generate the correct answer to the problem
2. Compare the student's answer with the correct answer
3. Identify what the student got right and wrong
4. Provide constructive feedback
5. Suggest improvements

Format your response as JSON with these fields:
{
  "correctAnswer": "...",
  "studentAnswer": "...",
  "isCorrect": true/false,
  "accuracy": 0-100 (percentage),
  "strengths": ["..."],
  "weaknesses": ["..."],
  "feedback": "...",
  "suggestions": ["..."]
}`
    });

    // Call Claude API with vision
    const message = await client.messages.create({
      model: 'claude-3-5-sonnet-20241022',
      max_tokens: 1024,
      messages: [
        {
          role: 'user',
          content: messageContent
        }
      ]
    });

    // Parse the response
    const responseText = message.content[0].text;
    let analysis;

    try {
      // Extract JSON from the response
      const jsonMatch = responseText.match(/\{[\s\S]*\}/);
      analysis = JSON.parse(jsonMatch ? jsonMatch[0] : responseText);
    } catch {
      analysis = {
        correctAnswer: 'See analysis',
        studentAnswer: 'See analysis',
        isCorrect: false,
        accuracy: 0,
        feedback: responseText,
        suggestions: []
      };
    }

    res.json({
      success: true,
      message: 'Answer processed successfully',
      analysis,
      rawResponse: responseText
    });
  } catch (error) {
    console.error('Error processing answer:', error);
    res.status(500).json({ error: error.message });
  }
};

export const compareAnswers = async (req, res) => {
  try {
    const { correctAnswer, studentAnswer, problemStatement } = req.body;

    if (!correctAnswer || !studentAnswer) {
      return res.status(400).json({ error: 'Both correctAnswer and studentAnswer are required' });
    }

    const message = await client.messages.create({
      model: 'claude-3-5-sonnet-20241022',
      max_tokens: 1024,
      messages: [
        {
          role: 'user',
          content: `Compare the following student answer with the correct answer for this problem:

PROBLEM: ${problemStatement || 'N/A'}

CORRECT ANSWER:
${correctAnswer}

STUDENT ANSWER:
${studentAnswer}

Provide a detailed comparison including:
1. What is correct
2. What is incorrect
3. Overall accuracy percentage
4. Specific feedback

Format as JSON with fields: isCorrect, accuracy, comparison, feedback, suggestions`
        }
      ]
    });

    const responseText = message.content[0].text;
    let analysis;

    try {
      const jsonMatch = responseText.match(/\{[\s\S]*\}/);
      analysis = JSON.parse(jsonMatch ? jsonMatch[0] : responseText);
    } catch {
      analysis = {
        isCorrect: false,
        accuracy: 0,
        feedback: responseText
      };
    }

    res.json({
      success: true,
      analysis
    });
  } catch (error) {
    console.error('Error comparing answers:', error);
    res.status(500).json({ error: error.message });
  }
};

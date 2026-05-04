export const createProblem = (req, res) => {
  try {
    const { problemStatement, subject, difficulty } = req.body;

    if (!problemStatement) {
      return res.status(400).json({ error: 'Problem statement is required' });
    }

    // Store problem in memory or database
    const problem = {
      id: Date.now().toString(),
      problemStatement,
      subject: subject || 'General',
      difficulty: difficulty || 'Medium',
      createdAt: new Date().toISOString()
    };

    res.json({
      success: true,
      message: 'Problem created successfully',
      problem
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const getProblem = (req, res) => {
  try {
    const { id } = req.params;
    
    res.json({
      success: true,
      message: 'Problem retrieved',
      problemId: id
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

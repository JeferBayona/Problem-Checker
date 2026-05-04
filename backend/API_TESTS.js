// Example test cases for the backend API
// Run with: curl commands or any API testing tool (Postman, Insomnia)

// ============================================
// 1. HEALTH CHECK
// ============================================
// GET http://localhost:5000/api/health

// Expected Response:
// {
//   "status": "OK",
//   "timestamp": "2024-05-04T10:30:00.000Z"
// }

// ============================================
// 2. CREATE PROBLEM
// ============================================
// POST http://localhost:5000/api/problem/create
// Content-Type: application/json

// Request:
{
  "problemStatement": "Solve for x: 2x + 5 = 15",
  "subject": "Mathematics",
  "difficulty": "Easy"
}

// Expected Response:
// {
//   "success": true,
//   "message": "Problem created successfully",
//   "problem": {
//     "id": "1234567890",
//     "problemStatement": "Solve for x: 2x + 5 = 15",
//     "subject": "Mathematics",
//     "difficulty": "Easy",
//     "createdAt": "2024-05-04T10:30:00.000Z"
//   }
// }

// ============================================
// 3. COMPARE TEXT ANSWERS
// ============================================
// POST http://localhost:5000/api/answer/compare
// Content-Type: application/json

// Request:
{
  "problemStatement": "Solve for x: 2x + 5 = 15",
  "correctAnswer": "x = 5",
  "studentAnswer": "x = 5"
}

// Expected Response:
// {
//   "success": true,
//   "analysis": {
//     "isCorrect": true,
//     "accuracy": 100,
//     "feedback": "Perfect! Your answer is correct.",
//     "suggestions": []
//   }
// }

// ============================================
// 4. PROCESS ANSWER WITH IMAGE
// ============================================
// POST http://localhost:5000/api/answer/process
// Content-Type: application/json

// Request:
{
  "problemStatement": "Solve for x: 2x + 5 = 15",
  "studentAnswerImage": "base64_encoded_image_data_here",
  "studentAnswerText": null
}

// Expected Response:
// {
//   "success": true,
//   "message": "Answer processed successfully",
//   "analysis": {
//     "correctAnswer": "x = 5",
//     "studentAnswer": "x = 5",
//     "isCorrect": true,
//     "accuracy": 100,
//     "strengths": ["Correct solution", "Clear handwriting"],
//     "weaknesses": [],
//     "feedback": "Excellent work! Your solution is correct.",
//     "suggestions": ["Consider showing intermediate steps"]
//   }
// }

// ============================================
// CURL EXAMPLES
// ============================================

// Test 1: Health Check
// curl http://localhost:5000/api/health

// Test 2: Create Problem
// curl -X POST http://localhost:5000/api/problem/create \
//   -H "Content-Type: application/json" \
//   -d '{
//     "problemStatement": "Solve for x: 2x + 5 = 15",
//     "subject": "Mathematics",
//     "difficulty": "Easy"
//   }'

// Test 3: Compare Answers
// curl -X POST http://localhost:5000/api/answer/compare \
//   -H "Content-Type: application/json" \
//   -d '{
//     "problemStatement": "Solve for x: 2x + 5 = 15",
//     "correctAnswer": "x = 5",
//     "studentAnswer": "x = 5"
//   }'

// ============================================
// ERROR CASES
// ============================================

// Missing required field:
// Request: POST /api/problem/create with missing problemStatement
// Response 400:
// {
//   "error": "Problem statement is required"
// }

// Server error:
// Response 500:
// {
//   "error": "Internal server error",
//   "status": 500
// }

// ============================================
// NOTES
// ============================================
// - All image data should be base64 encoded
// - Image size should be under 5MB
// - Claude API key must be set in .env
// - Server must be running (npm run dev)
// - Default port is 5000 (configurable in .env)

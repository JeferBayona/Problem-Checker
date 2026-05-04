# Problem Checker - AI-Powered Student Answer Verification

A mobile app for teachers to verify student answers using AI vision and comparison technology.

## Project Structure

```
Problem Checker/
├── backend/              # Node.js Express API
│   ├── src/
│   │   ├── controllers/
│   │   ├── routes/
│   │   └── index.js
│   ├── package.json
│   └── .env.example
└── android-app/          # Native Android Kotlin app
    ├── src/
    │   ├── main/
    │   │   ├── kotlin/
    │   │   ├── res/
    │   │   └── AndroidManifest.xml
    └── build.gradle
```

## Features

✅ **Problem Statement Input** - Teachers enter the problem
✅ **Camera Scanning** - Capture student's answer using device camera
✅ **AI Analysis** - Claude AI generates correct answer
✅ **Answer Comparison** - Compare student vs correct answer
✅ **Detailed Feedback** - Accuracy score, strengths, weaknesses, suggestions
✅ **Free APIs** - Uses Claude API (has free tier)

## Tech Stack

### Backend
- **Node.js** with Express.js
- **Claude API** (Anthropic) for AI processing
- **Multer** for image upload handling
- **Sharp** for image processing

### Frontend (Android)
- **Kotlin** for native development
- **CameraX** for camera functionality
- **Retrofit** for API calls
- **AndroidX** libraries

## Setup Instructions

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Install dependencies:
```bash
npm install
```

3. Create `.env` file from `.env.example`:
```bash
cp .env.example .env
```

4. Add your Claude API key:
```
CLAUDE_API_KEY=sk-ant-xxxxxxxxxxxxxxxxxxxxx
```

5. Start the server:
```bash
npm start
```

Server will run on `http://localhost:5000`

### Android App Setup

1. Install Android Studio
2. Open the `android-app` folder in Android Studio
3. Sync Gradle files
4. Configure the backend API URL in [ApiClient.kt](android-app/src/main/kotlin/com/example/problemchecker/ApiClient.kt)
5. Build and run on emulator or device

## API Endpoints

### POST `/api/problem/create`
Create a new problem statement

**Request:**
```json
{
  "problemStatement": "Solve: 2x + 5 = 15",
  "subject": "Mathematics",
  "difficulty": "Easy"
}
```

### POST `/api/answer/process`
Process student answer with image

**Request:**
```json
{
  "problemStatement": "Solve: 2x + 5 = 15",
  "studentAnswerImage": "base64_image_data"
}
```

### POST `/api/answer/compare`
Compare student answer with correct answer

**Request:**
```json
{
  "problemStatement": "Solve: 2x + 5 = 15",
  "correctAnswer": "x = 5",
  "studentAnswer": "x = 5"
}
```

## Environment Variables

Create a `.env` file in the backend folder:

```
PORT=5000
CLAUDE_API_KEY=your_api_key_here
NODE_ENV=development
```

## Getting Claude API Key

1. Visit https://console.anthropic.com
2. Sign up for a free account
3. Go to API keys section
4. Generate a new API key
5. Add it to `.env` file

## Free API Considerations

- **Claude API**: Free tier includes $5 credit per month
- Image size limits: Under 5MB per image
- Rate limiting: Check Anthropic documentation
- Monthly quota management recommended

## Development Notes

- Android app uses CameraX for modern camera access
- Backend processes images with Claude's vision capabilities
- Images can be sent as base64 in JSON
- Comparison uses Claude's text analysis

## Future Enhancements

- [ ] User authentication
- [ ] Answer history storage
- [ ] Batch processing multiple answers
- [ ] Different problem types support
- [ ] Performance analytics
- [ ] Mobile UI polish

## License

MIT

# Project Architecture

## System Overview

```
┌─────────────────────────────────────────────────────────────┐
│                     Problem Checker App                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────────────┐      ┌──────────────────────┐ │
│  │   Android Frontend       │      │  Backend Server      │ │
│  │  (Native Kotlin)         │◄────►│  (Node.js Express)   │ │
│  │                          │      │                      │ │
│  │ • MainActivity           │      │ • /api/problem/*     │ │
│  │ • ScanAnswerActivity     │      │ • /api/answer/*      │ │
│  │ • ResultActivity         │      │                      │ │
│  │ • Camera Integration     │      │ Dependencies:        │ │
│  │ • Image Capture          │      │ • Claude API client  │ │
│  │                          │      │ • Image processing   │ │
│  └──────────────────────────┘      └──────────────────────┘ │
│           │                                  │                │
│           └──────────────────┬───────────────┘                │
│                              │                                │
│                              ▼                                │
│                    ┌──────────────────┐                      │
│                    │  Claude API      │                      │
│                    │  (Vision & Text) │                      │
│                    │  Free Tier       │                      │
│                    └──────────────────┘                      │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow

### 1. Problem Input Flow
```
Teacher Input
    ↓
MainActivity (Text Input)
    ↓
/api/problem/create
    ↓
Problem stored in memory/DB
    ↓
Navigate to ScanAnswerActivity
```

### 2. Answer Scanning & Processing Flow
```
Camera Capture
    ↓
Image taken (JPEG)
    ↓
Convert to Base64
    ↓
POST /api/answer/process
    ↓
Backend receives image + problem
    ↓
Send to Claude API with vision
    ↓
Claude analyzes handwritten answer
    ↓
Generate correct answer
    ↓
Compare answers
    ↓
Return JSON analysis
    ↓
Display ResultActivity
```

### 3. API Request/Response

**Request Example:**
```json
{
  "problemStatement": "Solve: 2x + 5 = 15",
  "studentAnswerImage": "data:image/jpeg;base64,/9j/4AAQSkZJRg...",
  "studentAnswerText": null
}
```

**Response Example:**
```json
{
  "success": true,
  "analysis": {
    "correctAnswer": "x = 5",
    "studentAnswer": "x = 5",
    "isCorrect": true,
    "accuracy": 100,
    "strengths": ["Correct solution", "Clear working"],
    "weaknesses": [],
    "feedback": "Excellent work! Your solution is mathematically correct.",
    "suggestions": ["Show more steps for clarity"]
  }
}
```

## Directory Structure Explained

```
Problem Checker/
│
├── backend/                          # Node.js Express API server
│   ├── src/
│   │   ├── controllers/              # Business logic
│   │   │   ├── problemController.js  # Create/get problems
│   │   │   └── answerController.js   # Process & compare answers
│   │   ├── routes/                   # API endpoints
│   │   │   ├── problemRoutes.js      # /api/problem/* routes
│   │   │   └── answerRoutes.js       # /api/answer/* routes
│   │   └── index.js                  # Express app setup
│   ├── package.json                  # Dependencies
│   ├── .env.example                  # Environment template
│   └── .gitignore
│
├── android-app/                      # Native Android app
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   └── com/example/
│   │   │   │       └── problemchecker/
│   │   │   │           ├── MainActivity.kt         # Home screen
│   │   │   │           ├── ScanAnswerActivity.kt   # Camera screen
│   │   │   │           ├── ResultActivity.kt       # Results screen
│   │   │   │           └── ApiClient.kt            # Retrofit setup
│   │   │   ├── res/
│   │   │   │   ├── layout/          # XML layouts
│   │   │   │   ├── values/          # Strings, colors, themes
│   │   │   │   └── ...
│   │   │   └── AndroidManifest.xml  # App configuration
│   ├── build.gradle                 # Android build config
│   ├── settings.gradle              # Gradle settings
│   └── gradle.properties            # Gradle properties
│
├── README.md                         # Full documentation
├── QUICKSTART.md                     # Quick start guide
├── ARCHITECTURE.md                   # This file
└── workspace.code-workspace          # VS Code workspace
```

## Technology Choices

### Backend: Node.js + Express
- **Why:** Fast setup, JavaScript ecosystem, easy to deploy
- **Trade-offs:** Single-threaded, but sufficient for this use case

### Android: Kotlin + Native
- **Why:** Official Android language, better performance than React Native
- **Trade-offs:** Android-only, requires Android Studio

### Claude API
- **Why:** Best-in-class vision capabilities, free tier available, no setup needed
- **Alternative:** Google Gemini Vision (also free)

### CameraX
- **Why:** Modern Android camera framework, built-in lifecycle support
- **Alternative:** Camera2 API (lower level, more control)

## Key Components

### Backend Modules

**answerController.js**
- `processAnswer()`: Sends image + problem to Claude for analysis
- `compareAnswers()`: Compares text answers using Claude

**problemController.js**
- `createProblem()`: Create new problem
- `getProblem()`: Retrieve problem details

### Android Components

**MainActivity.kt**
- Problem input form
- Navigation to camera activity

**ScanAnswerActivity.kt**
- CameraX preview setup
- Photo capture with CameraX
- Image file saving

**ResultActivity.kt**
- Display AI analysis results
- Show accuracy, feedback, suggestions

**ApiClient.kt**
- Retrofit configuration
- Base URL setup
- API service definition

## Integration Points

### Android → Backend
- **Method:** HTTP POST (Retrofit)
- **Format:** JSON
- **Authentication:** None (currently - add later if needed)
- **Image Transfer:** Base64 encoding

### Backend → Claude API
- **Method:** HTTP POST
- **Format:** JSON with base64 images
- **Authentication:** API Key in Authorization header
- **Rate Limiting:** Check Anthropic docs

## Security Considerations

⚠️ **Current State:** Development only

**For Production:**
- [ ] Add API authentication (JWT tokens)
- [ ] Validate input on both frontend and backend
- [ ] Use HTTPS for all communications
- [ ] Implement rate limiting
- [ ] Store sensitive data securely
- [ ] Add CORS restrictions
- [ ] Implement image size/type validation

## Performance Considerations

- **Image Size:** Keep under 5MB for Claude API
- **Response Time:** ~2-5 seconds per analysis
- **Concurrent Users:** Single backend instance suitable for ~100 concurrent users

## Future Architecture Improvements

```
┌─────────────────┐
│   Web Admin     │  <- Teacher dashboard
└────────┬────────┘
         │
┌────────┴────────┐
│  API Gateway    │  <- Handle auth, rate limiting
└────────┬────────┘
         │
    ┌────┴─────────┬───────────┐
    │              │           │
    ▼              ▼           ▼
┌────────┐  ┌──────────┐  ┌───────────┐
│Problem │  │ Answer   │  │ Analytics │
│ Svc    │  │ Svc      │  │ Svc       │
└────────┘  └──────────┘  └───────────┘
    │              │           │
    └──────┬───────┴───────────┘
           │
      ┌────┴─────┐
      │  Database │
      └───────────┘
```

With microservices for:
- Problem management
- Answer processing
- Analytics & history
- User management

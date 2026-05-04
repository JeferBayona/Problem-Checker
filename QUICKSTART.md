# Quick Start Guide

## 1. Backend Setup (5 minutes)

### Prerequisites
- Node.js 16+ installed
- Claude API key from https://console.anthropic.com

### Steps
```bash
cd backend
npm install
cp .env.example .env
# Edit .env and add your CLAUDE_API_KEY
npm run dev
```

The API will start at `http://localhost:5000`

**Test the API:**
```bash
curl http://localhost:5000/api/health
```

## 2. Android App Setup

### Prerequisites
- Android Studio installed
- Android SDK 24+ (for minimum SDK support)
- Physical Android device or emulator

### Steps
1. Open Android Studio
2. File → Open → Select `android-app` folder
3. Wait for Gradle sync to complete
4. Update API URL in `src/main/kotlin/com/example/problemchecker/ApiClient.kt`
   - Change `BASE_URL` to your backend's IP address
5. Run on device: `Shift + F10` (or click Run button)

## 3. Testing the Flow

### Manual Testing

1. **On Android App:**
   - Input: "Solve 2x + 5 = 15"
   - Tap "Next: Scan Answer"
   - Take a photo of written answer "x = 5"
   - View results and feedback

2. **Test with curl (Backend only):**

```bash
# Test health check
curl http://localhost:5000/api/health

# Test problem creation
curl -X POST http://localhost:5000/api/problem/create \
  -H "Content-Type: application/json" \
  -d '{"problemStatement":"Solve: 2x+5=15","subject":"Math","difficulty":"Easy"}'

# Test answer comparison (text only)
curl -X POST http://localhost:5000/api/answer/compare \
  -H "Content-Type: application/json" \
  -d '{
    "problemStatement": "Solve: 2x+5=15",
    "correctAnswer": "x = 5",
    "studentAnswer": "x = 5"
  }'
```

## 4. Troubleshooting

### Backend Issues
- **Cannot find module**: Run `npm install` again
- **CLAUDE_API_KEY error**: Check `.env` file has valid key
- **Port 5000 in use**: Change PORT in `.env` or kill process using `netstat`

### Android Issues
- **Cannot connect to backend**: 
  - Use your computer's local IP (not localhost)
  - Check firewall settings
  - Ensure backend is running
- **Camera permission denied**: Grant permissions in app settings
- **Gradle sync fails**: File → Invalidate Caches → Restart

## 5. Next Steps

- [ ] Connect Android app to backend
- [ ] Implement image upload in camera activity
- [ ] Add database to store problem/answer history
- [ ] Deploy backend to cloud (Heroku, Render, etc.)
- [ ] Implement user authentication
- [ ] Add more problem types support

## Support

For issues, check:
- Backend logs: `npm run dev` terminal output
- Android logcat: View menu → Tool Windows → Logcat
- API docs in README.md

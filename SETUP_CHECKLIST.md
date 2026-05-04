# Setup Checklist

## Phase 1: Environment Preparation

### Claude API Setup
- [ ] Go to https://console.anthropic.com
- [ ] Create a free account
- [ ] Navigate to API Keys section
- [ ] Create a new API key
- [ ] Copy the API key (starts with `sk-ant-`)
- [ ] Save it safely (you'll need it for the backend)

### System Requirements Check
- [ ] Node.js 16+ installed (`node -v`)
- [ ] npm installed (`npm -v`)
- [ ] Android Studio installed
- [ ] Android SDK Platform 34 installed
- [ ] Git installed (optional but recommended)

## Phase 2: Backend Setup

### Installation
- [ ] Navigate to `backend` folder: `cd backend`
- [ ] Run `npm install`
- [ ] Copy `.env.example` to `.env`: `cp .env.example .env`
- [ ] Edit `.env` file and add your Claude API key:
  ```
  PORT=5000
  CLAUDE_API_KEY=sk-ant-xxxxxxxxxxxxxx
  NODE_ENV=development
  ```
- [ ] Save `.env` file

### Verification
- [ ] Run `npm run dev` to start backend
- [ ] Open browser: http://localhost:5000/api/health
- [ ] You should see: `{"status":"OK","timestamp":"..."}`
- [ ] Backend is working! ✅

### Testing
- [ ] Test create problem endpoint (see API_TESTS.js)
- [ ] Test compare answers endpoint
- [ ] Keep terminal running for next phase

## Phase 3: Android App Setup

### Project Import
- [ ] Open Android Studio
- [ ] Click "File" → "Open"
- [ ] Navigate to `android-app` folder
- [ ] Click "OK"
- [ ] Wait for Gradle sync to complete
- [ ] Close any error dialogs

### Configuration
- [ ] Find file: `src/main/kotlin/com/example/problemchecker/ApiClient.kt`
- [ ] Update `BASE_URL` with your backend IP:
  - If testing locally: Find your PC's IP address (run `ipconfig` in terminal)
  - Replace `your-backend-ip` with actual IP: `http://192.168.x.x:5000/api/`
- [ ] Save file

### Build & Run
- [ ] Connect Android device or start emulator
- [ ] Click "Run" button (green play icon) or press `Shift + F10`
- [ ] Wait for app to build and install (~1-2 minutes first time)
- [ ] App should launch on your device/emulator ✅

## Phase 4: End-to-End Testing

### App Flow Test
- [ ] Open Problem Checker app
- [ ] Input problem: "Solve for x: 2x + 5 = 15"
- [ ] Tap "Next: Scan Answer"
- [ ] Grant camera permissions when prompted
- [ ] Take photo of written answer
- [ ] Wait for backend to process (2-5 seconds)
- [ ] View results showing analysis ✅

### Backend Logs
- [ ] Check backend terminal for logs
- [ ] You should see POST requests being made
- [ ] No error messages should appear

## Phase 5: Troubleshooting

### Backend Won't Start
- [ ] Check if port 5000 is already in use
- [ ] Change PORT in `.env` to 5001 or 5002
- [ ] Restart with `npm run dev`

### Backend API Not Responding
- [ ] Verify Claude API key is correct
- [ ] Check internet connection
- [ ] Check `npm install` completed successfully
- [ ] Try: `npm install` again

### App Can't Connect to Backend
- [ ] Verify IP address in ApiClient.kt (not localhost)
- [ ] Check backend is still running
- [ ] Check if firewall is blocking port 5000
- [ ] Use same network for device and computer

### Camera Permission Issues
- [ ] Go to Settings → Apps → Problem Checker
- [ ] Enable Camera permission
- [ ] Clear app cache and try again

### Gradle Sync Failed
- [ ] File → Invalidate Caches → Restart
- [ ] File → Sync with Gradle Files
- [ ] Close and reopen Android Studio

## Phase 6: Development Tips

### Making Changes

**Backend:**
- Edit files in `backend/src/`
- Server auto-reloads with `npm run dev` ✅
- Test API with curl or Postman

**Android:**
- Edit Kotlin files
- Click Build → Rebuild Project
- Re-run app with Shift + F10
- Check Logcat for errors

### Useful Commands

Backend:
```bash
npm run dev      # Development mode with auto-reload
npm start        # Production mode
npm test         # Run tests (when added)
```

Android Studio:
```bash
Shift + F10      # Build and run
Ctrl + Shift + F10  # Debug
```

## Phase 7: Next Steps

### Short Term (This Week)
- [ ] Fully connect Android app to backend
- [ ] Implement image upload in camera activity
- [ ] Test with multiple problem types
- [ ] Refine UI/UX based on feedback

### Medium Term (This Month)
- [ ] Add database to store problems and answers
- [ ] Implement user authentication
- [ ] Add answer history/analytics
- [ ] Create admin dashboard

### Long Term (Future)
- [ ] Deploy backend to cloud (Heroku, Render)
- [ ] Add web interface for teachers
- [ ] Support more problem types
- [ ] Add OCR for better image processing

## Support Resources

- **Node.js Docs:** https://nodejs.org/docs/
- **Android Docs:** https://developer.android.com/docs
- **Claude API Docs:** https://docs.anthropic.com
- **Express.js Guide:** https://expressjs.com/
- **CameraX Guide:** https://developer.android.com/training/camerax

## Verification Checklist

Once all phases are complete:
- [ ] Backend runs without errors
- [ ] App opens and shows home screen
- [ ] Camera permission works
- [ ] Photo capture works
- [ ] Backend processes images
- [ ] Results display correctly
- [ ] No errors in backend logs
- [ ] No errors in Android Logcat

---

**Status:** ⏳ Ready to start!

**Estimated Time:** 30-45 minutes for complete setup

**Current Date:** May 4, 2026

import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import problemRoute from './routes/problemRoutes.js';
import answerRoute from './routes/answerRoutes.js';

dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ limit: '50mb', extended: true }));

// Routes
app.use('/api/problem', problemRoute);
app.use('/api/answer', answerRoute);

// Health check
app.get('/api/health', (req, res) => {
  res.json({ status: 'OK', timestamp: new Date().toISOString() });
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error('Error:', err);
  res.status(err.status || 500).json({
    error: err.message || 'Internal server error',
    status: err.status || 500
  });
});

app.listen(PORT, () => {
  console.log(`🚀 Problem Checker backend running on port ${PORT}`);
});

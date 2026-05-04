import express from 'express';
import { processAnswer, compareAnswers } from '../controllers/answerController.js';

const router = express.Router();

router.post('/process', processAnswer);
router.post('/compare', compareAnswers);

export default router;

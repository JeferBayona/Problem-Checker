import express from 'express';
import { createProblem, getProblem } from '../controllers/problemController.js';

const router = express.Router();

router.post('/create', createProblem);
router.get('/:id', getProblem);

export default router;

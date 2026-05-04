package com.example.problemchecker

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultText = findViewById<TextView>(R.id.resultText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        // Get data from intent
        val problemStatement = intent.getStringExtra("PROBLEM_STATEMENT") ?: ""
        val correctAnswer = intent.getStringExtra("CORRECT_ANSWER") ?: ""
        val studentAnswer = intent.getStringExtra("STUDENT_ANSWER") ?: ""
        val isCorrect = intent.getBooleanExtra("IS_CORRECT", false)
        val accuracy = intent.getIntExtra("ACCURACY", 0)
        val feedback = intent.getStringExtra("FEEDBACK") ?: ""
        val strengths = intent.getStringArrayExtra("STRENGTHS") ?: arrayOf()
        val weaknesses = intent.getStringArrayExtra("WEAKNESSES") ?: arrayOf()
        val suggestions = intent.getStringArrayExtra("SUGGESTIONS") ?: arrayOf()

        // Build comprehensive result display
        val resultStatus = if (isCorrect) "✓ CORRECT!" else "✗ INCORRECT"
        val resultColor = if (isCorrect) "#00AA00" else "#FF0000"

        val detailedResult = buildString {
            append("═════════════════════════════════\n")
            append("ANALYSIS RESULT\n")
            append("═════════════════════════════════\n\n")
            append("STATUS: $resultStatus\n")
            append("ACCURACY: $accuracy%\n\n")
            
            append("PROBLEM:\n")
            append("$problemStatement\n\n")
            
            append("─────────────────────────────────\n")
            append("CORRECT ANSWER:\n")
            append("$correctAnswer\n\n")
            
            append("─────────────────────────────────\n")
            append("STUDENT'S ANSWER:\n")
            append("$studentAnswer\n\n")
            
            if (strengths.isNotEmpty()) {
                append("─────────────────────────────────\n")
                append("STRENGTHS:\n")
                strengths.forEach { append("✓ $it\n") }
                append("\n")
            }
            
            if (weaknesses.isNotEmpty()) {
                append("─────────────────────────────────\n")
                append("AREAS FOR IMPROVEMENT:\n")
                weaknesses.forEach { append("✗ $it\n") }
                append("\n")
            }
            
            append("─────────────────────────────────\n")
            append("FEEDBACK:\n")
            append("$feedback\n\n")
            
            if (suggestions.isNotEmpty()) {
                append("─────────────────────────────────\n")
                append("SUGGESTIONS:\n")
                suggestions.forEach { append("→ $it\n") }
            }
            
            append("═════════════════════════════════\n")
        }

        resultText.text = resultStatus
        feedbackText.text = detailedResult
        feedbackText.isVerticalScrollBarEnabled = true
    }
}

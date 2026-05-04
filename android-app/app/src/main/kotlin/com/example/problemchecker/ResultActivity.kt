package com.example.problemchecker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultText = findViewById<TextView>(R.id.resultText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        val isCorrect = intent.getBooleanExtra("IS_CORRECT", false)
        val feedback = intent.getStringExtra("FEEDBACK") ?: ""

        resultText.text = if (isCorrect) "✓ Correct!" else "✗ Incorrect"
        feedbackText.text = feedback
    }
}

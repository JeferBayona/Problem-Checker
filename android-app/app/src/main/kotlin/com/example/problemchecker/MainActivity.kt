package com.example.problemchecker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val problemInput = findViewById<EditText>(R.id.problemInput)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val problemStatement = problemInput.text.toString()
            if (problemStatement.isNotEmpty()) {
                // Navigate to ScanAnswerActivity with problem statement
                val intent = Intent(this, ScanAnswerActivity::class.java)
                intent.putExtra("PROBLEM_STATEMENT", problemStatement)
                startActivity(intent)
            }
        }
    }
}

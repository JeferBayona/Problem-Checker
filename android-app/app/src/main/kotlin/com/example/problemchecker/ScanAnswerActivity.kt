package com.example.problemchecker

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanAnswerActivity : AppCompatActivity() {
    private lateinit var viewFinder: PreviewView
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var problemStatement: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_answer)

        problemStatement = intent.getStringExtra("PROBLEM_STATEMENT") ?: ""

        viewFinder = findViewById(R.id.viewFinder)
        val captureButton = findViewById<Button>(R.id.captureButton)

        cameraExecutor = Executors.newSingleThreadExecutor()

        // Request permissions first
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        captureButton.setOnClickListener {
            // Check permissions before taking photo
            if (allPermissionsGranted()) {
                takePhoto()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required. Please grant it in Settings.",
                    Toast.LENGTH_LONG
                ).show()
                // Request permissions again
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(this, "Camera binding failed", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val timeStamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())

        val metadata = ImageCapture.Metadata().apply {
            isReversedHorizontal = false
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(android.provider.MediaStore.Images.Media.DISPLAY_NAME, "$timeStamp.jpg")
            }
        ).setMetadata(metadata).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(this@ScanAnswerActivity, "Photo capture failed: ${exc.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(this@ScanAnswerActivity, "Processing answer...", Toast.LENGTH_SHORT).show()
                    
                    // Read the saved image and convert to base64
                    val inputStream = output.savedUri?.let { contentResolver.openInputStream(it) }
                    if (inputStream != null) {
                        try {
                            val imageBytes = inputStream.readBytes()
                            val base64Image = android.util.Base64.encodeToString(imageBytes, android.util.Base64.NO_WRAP)
                            
                            // Send to backend for AI analysis
                            sendAnswerToBackend(base64Image)
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@ScanAnswerActivity,
                                "Error processing image: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } finally {
                            inputStream.close()
                        }
                    }
                }
            }
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun sendAnswerToBackend(base64Image: String) {
        val problemStatement = problemStatement
        if (problemStatement.isEmpty()) {
            Toast.makeText(this, "Problem statement missing", Toast.LENGTH_SHORT).show()
            return
        }

        // Launch coroutine to call backend
        lifecycleScope.launch {
            try {
                val apiService = ApiClient.getInstance(this@ScanAnswerActivity)
                    .create(ApiService::class.java)
                
                val request = ProcessAnswerRequest(
                    problemStatement = problemStatement,
                    studentAnswerImage = base64Image
                )
                
                val response = apiService.processAnswer(request)
                
                if (response.success) {
                    // Navigate to ResultActivity with analysis results
                    val intent = Intent(this@ScanAnswerActivity, ResultActivity::class.java).apply {
                        putExtra("PROBLEM_STATEMENT", problemStatement)
                        putExtra("CORRECT_ANSWER", response.analysis.correctAnswer)
                        putExtra("STUDENT_ANSWER", response.analysis.studentAnswer)
                        putExtra("IS_CORRECT", response.analysis.isCorrect)
                        putExtra("ACCURACY", response.analysis.accuracy)
                        putExtra("FEEDBACK", response.analysis.feedback)
                        putExtra("STRENGTHS", response.analysis.strengths.toTypedArray())
                        putExtra("WEAKNESSES", response.analysis.weaknesses.toTypedArray())
                        putExtra("SUGGESTIONS", response.analysis.suggestions.toTypedArray())
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@ScanAnswerActivity,
                        "Error: ${response.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ScanAnswerActivity,
                    "Network error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "CameraX-App"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                // Only request WRITE_EXTERNAL_STORAGE for Android 9 and below
                if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}

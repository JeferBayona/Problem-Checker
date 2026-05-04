package com.example.problemchecker

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import com.google.gson.annotations.SerializedName

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"  // Android emulator localhost

    fun getInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

interface ApiService {
    @POST("answer/processAnswer")
    suspend fun processAnswer(@Body request: ProcessAnswerRequest): ProcessAnswerResponse

    @POST("problem/create")
    suspend fun createProblem(@Body request: CreateProblemRequest): CreateProblemResponse
}

data class ProcessAnswerRequest(
    val problemStatement: String,
    val studentAnswerImage: String? = null,
    val studentAnswerText: String? = null
)

data class ProcessAnswerResponse(
    val success: Boolean,
    val message: String,
    val analysis: AnalysisResult
)

data class AnalysisResult(
    val correctAnswer: String,
    val studentAnswer: String,
    val isCorrect: Boolean,
    val accuracy: Int,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val feedback: String,
    val suggestions: List<String>
)

data class CreateProblemRequest(
    val problemStatement: String,
    val subject: String = "General",
    val difficulty: String = "Medium"
)

data class CreateProblemResponse(
    val success: Boolean,
    val message: String,
    val problem: ProblemData
)

data class ProblemData(
    val id: String,
    val problemStatement: String,
    val subject: String,
    val difficulty: String,
    val createdAt: String
)

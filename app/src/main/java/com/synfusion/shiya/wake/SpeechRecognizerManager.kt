package com.synfusion.shiya.wake

import android.content.Context
import android.util.Log
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.io.IOException

class SpeechRecognizerManager(
    private val context: Context,
    private val listener: WakeWordListener
) : RecognitionListener {

    private var model: Model? = null
    private var speechService: SpeechService? = null
    private val TAG = "SpeechRecognizerManager"

    private val wakeWords = listOf("hey shiya", "hello shiya", "shiya sun")

    init {
        initModel()
    }

    private fun initModel() {
        StorageService.unpack(context, "models/vosk/model", "model",
            { model ->
                this.model = model
                Log.d(TAG, "Vosk model loaded successfully")
                startListening()
            },
            { exception ->
                Log.e(TAG, "Failed to unpack the model: \${exception.message}", exception)
            }
        )
    }

    fun startListening() {
        if (model == null) {
            Log.w(TAG, "Model is not loaded yet")
            return
        }

        if (speechService != null) {
            Log.w(TAG, "Speech service is already running")
            return
        }

        try {
            val recognizer = Recognizer(model, 16000.0f)
            speechService = SpeechService(recognizer, 16000.0f)
            speechService?.startListening(this)
            Log.d(TAG, "Started listening on microphone")
        } catch (e: IOException) {
            Log.e(TAG, "Failed to start speech service: \${e.message}", e)
        }
    }

    fun stopListening() {
        speechService?.let {
            it.stop()
            it.shutdown()
            Log.d(TAG, "Stopped listening on microphone")
        }
        speechService = null
    }

    override fun onPartialResult(hypothesis: String) {
        val partialText = parseHypothesis(hypothesis, "partial")
        if (partialText.isNotBlank()) {
            Log.d(TAG, "Partial result: \$partialText")
            checkWakeWord(partialText)
        }
    }

    override fun onResult(hypothesis: String) {
        val text = parseHypothesis(hypothesis, "text")
        if (text.isNotBlank()) {
            Log.d(TAG, "Final result: \$text")
            checkWakeWord(text)
        }
    }

    override fun onFinalResult(hypothesis: String) {
        val text = parseHypothesis(hypothesis, "text")
        if (text.isNotBlank()) {
            Log.d(TAG, "Final result (end): \$text")
            checkWakeWord(text)
        }
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "Recognizer error: \${exception.message}", exception)
    }

    override fun onTimeout() {
        Log.d(TAG, "Recognizer timeout")
    }

    private fun parseHypothesis(jsonString: String, key: String): String {
        return try {
            val jsonObject = JSONObject(jsonString)
            jsonObject.optString(key, "")
        } catch (e: Exception) {
            ""
        }
    }

    private fun checkWakeWord(text: String) {
        val lowerText = text.lowercase()
        for (wakeWord in wakeWords) {
            if (lowerText.contains(wakeWord)) {
                Log.i(TAG, "Wake word detected: \$wakeWord")
                listener.onWakeWordDetected()
                break
            }
        }
    }
}

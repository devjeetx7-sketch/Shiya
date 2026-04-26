package com.synfusion.shiya.wake

class SpeechRecognizerManager : WakeWordListener {

    override fun startListening() {
        // Placeholder for initializing Vosk offline model
        // Model will be placed in assets/models/vosk/
    }

    override fun stopListening() {
        // Placeholder for stopping recognizer
    }

    override fun onWakeWordDetected() {
        // Triggered when "Hello Shiya", "Hey Shiya", or "Shiya sun" is detected
    }
}

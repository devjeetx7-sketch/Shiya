package com.synfusion.shiya.wake

interface WakeWordListener {
    fun startListening()
    fun stopListening()
    fun onWakeWordDetected()
}

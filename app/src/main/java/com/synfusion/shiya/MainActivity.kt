package com.synfusion.shiya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synfusion.shiya.theme.ShiyaTheme
import com.synfusion.shiya.ui.SiriOrbAnimation
import com.synfusion.shiya.utils.PermissionHelper

class MainActivity : ComponentActivity() {

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // In Step 1, just log or ignore. We requested everything.
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()

        setContent {
            ShiyaTheme {
                HomeScreen()
            }
        }
    }

    private fun requestPermissions() {
        if (!PermissionHelper.hasAllPermissions(this)) {
            requestPermissionsLauncher.launch(PermissionHelper.REQUIRED_PERMISSIONS)
        }
        if (!PermissionHelper.hasOverlayPermission(this)) {
            PermissionHelper.requestOverlayPermission(this)
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SiriOrbAnimation(isListening = true)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Say Hello Shiya",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 1.5.sp
            )
        }
    }
}

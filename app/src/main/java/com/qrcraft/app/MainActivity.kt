package com.qrcraft.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.QRCraftTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_QRCraft)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        installSplashScreen()

        setContent {
            QRCraftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavigationRoot(
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}
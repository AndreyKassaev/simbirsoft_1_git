package com.kassaev.simbirsoft_1_git

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.rememberNavController
import com.kassaev.simbirsoft_1_git.core.ui.theme.Simbirsoft_1_gitTheme
import com.kassaev.simbirsoft_1_git.feature.event.service.BatteryChargingNotificationService
import com.kassaev.simbirsoft_1_git.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

const val SPLASH_SCREEN_DELAY = 1500L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Simbirsoft_1_git)
        super.onCreate(savedInstanceState)
        startService(Intent(this,BatteryChargingNotificationService::class.java))
        enableEdgeToEdge()
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_launcher)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            val runnable = Runnable {
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), null)
                setContentCompose()
            }
            onBackPressedDispatcher.addCallback(this) {
                handler.removeCallbacksAndMessages(runnable)
                finish()
            }
            handler.postDelayed(runnable, SPLASH_SCREEN_DELAY)
        } else {
            setContentCompose()
        }
    }

    private fun setContentCompose() {
        setContent {
            val navController = rememberNavController()
            Simbirsoft_1_gitTheme {
                val eventId = intent?.data?.getQueryParameter("eventId")
                Navigation(
                    navController = navController,
                    eventId = eventId
                )
            }
        }
    }
}
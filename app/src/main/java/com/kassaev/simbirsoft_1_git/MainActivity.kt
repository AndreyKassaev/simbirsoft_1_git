package com.kassaev.simbirsoft_1_git

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kassaev.simbirsoft_1_git.navigation.Navigation
import com.kassaev.simbirsoft_1_git.core.ui.theme.Simbirsoft_1_gitTheme
import dagger.hilt.android.AndroidEntryPoint

const val SPLASH_SCREEN_DELAY = 1500L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Simbirsoft_1_git)
        super.onCreate(savedInstanceState)
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
            Simbirsoft_1_gitTheme {
                Navigation()
            }
        }
    }
}
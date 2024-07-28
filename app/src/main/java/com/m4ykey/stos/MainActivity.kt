package com.m4ykey.stos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.m4ykey.stos.ui.question.QuestionList
import com.m4ykey.stos.ui.question.QuestionViewModel
import com.m4ykey.stos.ui.theme.StosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel : QuestionViewModel by viewModels()

        setContent {
            StosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
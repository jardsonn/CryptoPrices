package com.jalloft.cryptoprices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jalloft.cryptoprices.ui.screens.Home
import com.jalloft.cryptoprices.ui.theme.CryptoPricesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoPricesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Home()
                }
            }
        }
    }
}
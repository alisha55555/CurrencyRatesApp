package com.example.currencyratesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CurrencyApp()
            }
        }
    }

    @Composable
    fun CurrencyApp() {
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        var currencyList by remember { mutableStateOf<List<String>>(emptyList()) }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        // Загрузка данных через DataLoader
        LaunchedEffect(Unit) {
            DataLoader { result ->
                if (result.isNotEmpty()) {
                    currencyList = result
                    isLoading = false
                } else {
                    errorMessage = "Ошибка загрузки данных"
                    isLoading = false
                }
            }.execute("https://open.er-api.com/v6/latest/USD")
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Поле для фильтрации
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Отображение ошибок или загрузки
            when {
                isLoading -> {
                    Text(text = "Загрузка данных...", modifier = Modifier.padding(4.dp))
                }
                errorMessage != null -> {
                    Text(text = errorMessage ?: "Неизвестная ошибка", modifier = Modifier.padding(4.dp))
                }
                else -> {
                    // Фильтрация списка
                    val filteredList = if (searchQuery.text.isEmpty()) {
                        currencyList
                    } else {
                        currencyList.filter { it.contains(searchQuery.text, ignoreCase = true) }
                    }

                    // Отображение валют
                    filteredList.forEach { currency ->
                        Text(text = currency, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}

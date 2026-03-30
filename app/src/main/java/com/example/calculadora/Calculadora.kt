package com.example.calculadora.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Calculadora() {

    var display by remember { mutableStateOf("0") }
    var firstNumber by remember { mutableStateOf<Double?>(null) }
    var operation by remember { mutableStateOf<String?>(null) }

    fun onNumberClick(number: String) {
        display = if (display == "0") number else display + number
    }

    fun onOperationClick(op: String) {
        firstNumber = display.toDoubleOrNull()
        operation = op
        display = "0"
    }

    fun onEqualClick() {
        val secondNumber = display.toDoubleOrNull()

        if (firstNumber == null || secondNumber == null || operation == null) {
            display = "Erro"
            return
        }

        val result = when (operation) {
            "+" -> firstNumber!! + secondNumber
            "-" -> firstNumber!! - secondNumber
            "*" -> firstNumber!! * secondNumber
            "/" -> {
                if (secondNumber == 0.0) {
                    display = "Erro"
                    return
                }
                firstNumber!! / secondNumber
            }
            else -> 0.0
        }

        display = result.toString()
        firstNumber = null
        operation = null
    }

    fun onClear() {
        display = "0"
        firstNumber = null
        operation = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Visor
        Text(
            text = display,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        // Botões
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("C", "0", "=", "+")
        )

        Column {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    in "0".."9" -> onNumberClick(label)
                                    "+", "-", "*", "/" -> onOperationClick(label)
                                    "=" -> onEqualClick()
                                    "C" -> onClear()
                                }
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text(text = label)
                        }
                    }
                }
            }
        }
    }
}
package com.example.practicacalculadora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicacalculadora.ui.theme.PracticaCalculadoraTheme

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf("") }
    var prevNumber by remember { mutableIntStateOf(0) }
    var currentOperation by remember { mutableStateOf("") }
    var memoryValue by remember { mutableStateOf(0) }
    var isResultFinal by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(8.dp)) {
        Row {
            Text(
                text = result.ifEmpty { "0" },
                fontSize = 24.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Text(
                text = if (memoryValue != 0) "M" else "",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        McOperation(
            onMcClick = {
                memoryValue = 0
            },
            onMrClick = {
                result = memoryValue.toString()
                isResultFinal = true
            },
            onMPlusClick = {
                val current = result.toIntOrNull() ?: 0
                memoryValue += current
            },
            onMMinusClick = {
                val current = result.toIntOrNull() ?: 0
                memoryValue -= current
            },
            hasMemory = memoryValue != 0
        )

        NumberPanel(
            onNumberClick = { num ->
                result = if (isResultFinal) num else result + num
                isResultFinal = false
            }
        )

        OperationsPanel(
            selectOperationType = { theOperation ->
                result.toIntOrNull()?.let {
                    prevNumber = it
                    result = ""
                    currentOperation = theOperation
                }
            },
            onEqualsClick = {
                val currentNumber = result.toIntOrNull() ?: 0
                val operationResult = when (currentOperation) {
                    "+" -> prevNumber + currentNumber
                    "-" -> prevNumber - currentNumber
                    "x" -> prevNumber * currentNumber
                    "/" -> if (currentNumber != 0) prevNumber / currentNumber else 0
                    else -> currentNumber
                }
                result = operationResult.toString()
                currentOperation = ""
                prevNumber = 0
                isResultFinal = true
            }
        )

        ClearOperationsPanel(
            onClearOneClick = {
                if (result.isNotEmpty()) result = result.dropLast(1)
            },
            onClearAllClick = {
                result = ""
                currentOperation = ""
                prevNumber = 0
                isResultFinal = false
            }
        )
    }
}

@Composable
fun ClearOperationsPanel(
    onClearOneClick: () -> Unit = {},
    onClearAllClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "C",
            onClick = { onClearOneClick() },
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "CE",
            onClick = { onClearAllClick() },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun OperationsPanel(
    selectOperationType: (operation: String) -> Unit,
    onEqualsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            text = "+",
            onClick = { selectOperationType("+") },
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "-",
            onClick = { selectOperationType("-") },
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "x",
            onClick = { selectOperationType("x") },
            modifier = Modifier.weight(1f)
        )
        TextButton(
            text = "/",
            onClick = { selectOperationType("/") },
            modifier = Modifier.weight(1f)
        )
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TextButton(
            text = "=",
            onClick = { onEqualsClick() },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun McOperation(
    onMcClick: () -> Unit,
    onMrClick: () -> Unit,
    onMPlusClick: () -> Unit,
    onMMinusClick: () -> Unit,
    hasMemory: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "MC",
            onClick = onMcClick,
            modifier = Modifier.weight(1f),
            enabled = hasMemory
        )

        TextButton(
            text = "MR",
            onClick = onMrClick,
            modifier = Modifier.weight(1f),
            enabled = hasMemory
        )

        TextButton(
            text = "M+",
            onClick = onMPlusClick,
            modifier = Modifier.weight(1f),
            enabled = true
        )

        TextButton(
            text = "M-",
            onClick = onMMinusClick,
            modifier = Modifier.weight(1f),
            enabled = true
        )
    }
}

@Composable
fun NumberPanel(
    onNumberClick: (num: String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "1",
            onClick = {
                onNumberClick("1")
            },
            modifier = Modifier
                .weight(1f),
        )
        TextButton(
            text = "2",
            onClick = {
                onNumberClick("2")

            },
            modifier = Modifier
                .weight(1f),
        )
        TextButton(
            text = "3",
            onClick = {
                onNumberClick("3")

            },
            modifier = Modifier
                .weight(1f),
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "4",
            onClick = {
                onNumberClick("4")
            },
            modifier = Modifier
                .weight(1f)
        )
        TextButton(
            text = "5",
            onClick = {
                onNumberClick("5")
            },
            modifier = Modifier
                .weight(1f)
        )
        TextButton(
            text = "6",
            onClick = {
                onNumberClick("6")
            },
            modifier = Modifier
                .weight(1f)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "7",
            onClick = {
                onNumberClick("7")
            },
            modifier = Modifier
                .weight(1f)
        )
        TextButton(
            text = "8",
            onClick = {
                onNumberClick("8")
            },
            modifier = Modifier
                .weight(1f)
        )
        TextButton(
            text = "9",
            onClick = {
                onNumberClick("9")
            },
            modifier = Modifier
                .weight(1f)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            text = "0",
            onClick = {
                onNumberClick("0")
            },
            modifier = Modifier
                .weight(1f)
        )

    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun ClearOperationsPanelPreview() {
    PracticaCalculadoraTheme {
        Column {
            ClearOperationsPanel(onClearOneClick = {}, onClearAllClick = {})
        }
    }
}

@Preview
@Composable
fun OperationsPanelPreview() {
    PracticaCalculadoraTheme {
        Column {
            OperationsPanel(selectOperationType = {}, onEqualsClick = {})
        }
    }
}

@Preview
@Composable
fun NumberPanelPreview() {
    PracticaCalculadoraTheme {
        Column { NumberPanel(onNumberClick = {}) }
    }
}

@Preview(showBackground = true)
@Composable
fun McOperationPreview() {
    PracticaCalculadoraTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            McOperation(
                onMcClick = {},
                onMrClick = {},
                onMPlusClick = {},
                onMMinusClick = {},
                hasMemory = true
            )
        }
    }
}
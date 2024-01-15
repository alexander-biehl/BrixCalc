package projects.brewers.amateur.com.brixcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import projects.brewers.amateur.com.brixcalc.ui.theme.BrixCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrixCalcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BrixCalcApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun BrixCalcApp(modifier: Modifier = Modifier) {
    var brixToSgSelected by remember { mutableStateOf(true) }
    var input by remember { mutableStateOf("") }

    val inputValue = input.toDoubleOrNull()

    val calculatedResult = 0.0

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Radios(
            brixToSgSelected,
            onRadioClicked = { brixToSgSelected = !brixToSgSelected },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier)

        CalculatorOutput(
            calculatedValue = calculatedResult.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp)
        )
        Spacer(modifier = Modifier)

        CalculatorInput(
            userInput = input,
            onUserInput = { },
            onCalculateClicked = { /*TODO*/ },
            onCancelClicked = { },
            isBrix = brixToSgSelected,
            modifier = Modifier
        )
    }
}

@Composable
fun CalculatorInput(
    userInput: String,
    onUserInput: (String) -> Unit,
    onCalculateClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    isBrix: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = onUserInput,
                label = {
                    if (isBrix) {
                        Text(text = stringResource(id = R.string.brix_to_sg))
                    } else {
                        Text(text = stringResource(id = R.string.sg_to_brix))
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onCalculateClicked() }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = onCalculateClicked,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.submit_button))
                }

                OutlinedButton(
                    onClick = onCancelClicked,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.cancel_button))
                }
            }
        }
    }
}

@Composable
fun CalculatorOutput(
    calculatedValue: String = "",
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                Text(
                    text = stringResource(R.string.result_label),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.border(width = 1.dp, color = Color.Black)
                ) {
                    Text(text = calculatedValue)
                }
            }
        }
    }
}

@Composable
fun Radios(
    brixToSgSelected: Boolean,
    onRadioClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            RadioButton(
                selected = brixToSgSelected,
                onClick = onRadioClicked
            )
            Text(text = stringResource(R.string.brix_to_sg))

            RadioButton(
                selected = !brixToSgSelected,
                onClick = onRadioClicked
            )
            Text(text = stringResource(R.string.sg_to_brix))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrixCalcTheme {
        BrixCalcApp(modifier = Modifier.fillMaxSize())
        // BrixCalcApp()
    }
}
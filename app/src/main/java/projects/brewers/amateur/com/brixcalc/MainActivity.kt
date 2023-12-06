package projects.brewers.amateur.com.brixcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RadioButton(selected = brixToSgSelected, onClick = {
                if (!brixToSgSelected) {
                    brixToSgSelected = true
                }
            })
            Text(text = stringResource(R.string.brix_to_sg))
            RadioButton(selected = !brixToSgSelected, onClick = {
                if (brixToSgSelected) {
                    brixToSgSelected = false
                }
            })
            Text(text = stringResource(R.string.sg_to_brix))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrixCalcTheme {
        BrixCalcApp(modifier = Modifier.fillMaxSize())
    }
}
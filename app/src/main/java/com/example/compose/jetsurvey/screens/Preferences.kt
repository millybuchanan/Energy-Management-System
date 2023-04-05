package com.example.compose.jetsurvey.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.question.SliderQuestion
import com.example.compose.jetsurvey.theme.JetsurveyTheme

@Composable
fun PreferencesScreen() {
    Column {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_energy_consumption,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.low_consumption,
                    neutralTextResource = R.string.mid_consumption,
                    endTextResource = R.string.high_consumption
                )
            }
        }

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_brightness,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.low_brightness,
                    neutralTextResource = R.string.mid_brightness,
                    endTextResource = R.string.high_brightness
                )
            }
        }

        JetsurveyTheme() {
            Surface {
                SliderQuestion(
                    titleResourceId = R.string.preference_temperature,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.low_temperature,
                    neutralTextResource = R.string.mid_temperature,
                    endTextResource = R.string.high_temperature
                )
            }
        }

        // Add more preference options here
    }
}


package com.example.compose.jetsurvey.survey.question

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.QuestionWrapper
import com.example.compose.jetsurvey.theme.JetsurveyTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFieldQuestion(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    ) {
    var text by rememberSaveable { mutableStateOf("") }
    QuestionWrapper(
        titleResourceId = titleResourceId,
        modifier = modifier,
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Age") },
            placeholder = { Text("My age is ...") },

            )
    }

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ZipCodeQuestion(flag: Boolean) {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("ZipCode") },
        placeholder = { Text("My zipcode is ...") }
    )
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun IdealIndoorTempQuestionPreview() {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Ideal Indoor Temp") },
        placeholder = { Text("Temperature in Fahrenheit ...") }
    )
}

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.question.*
import kotlin.reflect.KFunction1



//@Composable
//fun GenderQuestion(
//    selectedAnswer: List<Int>,
//    onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    RadioButton(
//        text = R.string.age,
//        selected = R.string.select_one,
//        onOptionSelected = { onOptionSelected })
//}

//also add temperature question here


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeQuestion(
    value: Int?,
    onValueChange: KFunction1<Int, Unit>,
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf(value?.toString() ?: "") }
    QuestionWrapper(
        titleResourceId = titleResourceId,
        modifier = modifier,
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                val age = newText.toIntOrNull()
                if (age != null) {
                    onValueChange(age)
                }
            },
            label = { Text("Age") },
            placeholder = { Text("My age is ...") },
            modifier = modifier,
        )
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ZipCodeQuestion(
    value: Int?,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes titleResourceId: Int,
    ){

    var text by rememberSaveable { mutableStateOf("") }

    QuestionWrapper(titleResourceId = titleResourceId, modifier = modifier) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                val zip = newText.toIntOrNull()
                if (zip != null) {
                    onValueChange(zip)
                } },
            label = { Text("ZipCode") },
            placeholder = { Text("My zipcode is ...") },
            modifier = modifier
        )
    }

}


@Composable
fun BrightnessQuestion(
    value: Float?,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    SliderQuestion(
        titleResourceId = R.string.brightness,
        value = value,
        onValueChange = onValueChange,
        startTextResource = R.string.very_dim,
        neutralTextResource = R.string.neutral,
        endTextResource = R.string.very_bright,
        modifier = modifier,
    )
}


@Composable
fun GenderQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        titleResourceId = R.string.gender,
        directionsResourceId = R.string.select_one,
        possibleAnswers = listOf(
            R.string.female,
            R.string.male,
            R.string.nonbinary,
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun FreeTimeQuestion(
    selectedAnswers: List<Int>,
    onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    MultipleChoiceQuestion(
        titleResourceId = R.string.age,
        directionsResourceId = R.string.select_all,
        possibleAnswers = listOf(
            R.string.draw,
            R.string.play_games,
            R.string.dance,
            R.string.watch_movies,
        ),
        selectedAnswers = selectedAnswers,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}


@Composable
fun TakeawayQuestion(
    dateInMillis: Long?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DateQuestion(
        titleResourceId = R.string.takeaway,
        directionsResourceId = R.string.select_date,
        dateInMillis = dateInMillis,
        onClick = onClick,
        modifier = modifier,

    )
}

@Composable
fun FeelingAboutSelfiesQuestion(
    value: Float?,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    SliderQuestion(
        titleResourceId = R.string.selfies,
        value = value,
        onValueChange = onValueChange,
        startTextResource = R.string.strongly_dislike,
        neutralTextResource = R.string.neutral,
        endTextResource = R.string.strongly_like,
        modifier = modifier,
    )
}

@Composable
fun TakeSelfieQuestion(
    imageUri: Uri?,
    getNewImageUri: () -> Uri,
    onPhotoTaken: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoQuestion(
        titleResourceId = R.string.selfie_skills,
        imageUri = imageUri,
        getNewImageUri = getNewImageUri,
        onPhotoTaken = onPhotoTaken,
        modifier = modifier,
    )
}

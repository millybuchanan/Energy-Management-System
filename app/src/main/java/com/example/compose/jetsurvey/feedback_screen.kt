/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.compose.jetsurvey.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.QuestionWrapper
import com.example.compose.jetsurvey.survey.question.SliderQuestion
import com.example.compose.jetsurvey.theme.JetsurveyTheme


@Composable
@Preview
fun FeedbackQuestionPreview() {
    JetsurveyTheme() {
        Column {
            Row  {
                Text(
                    text = stringResource(R.string.Feeling),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.8f)

                )
            }
            Row  {
                SliderQuestion(
                    titleResourceId = R.string.FeedbackTemp,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.TooCold,
                    neutralTextResource = R.string.Satisfactory,
                    endTextResource = R.string.TooHot,
                )
            }
            Row  {
                SliderQuestion(
                    titleResourceId = R.string.FeedbackHumidity,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.TooDry,
                    neutralTextResource = R.string.Satisfactory,
                    endTextResource = R.string.TooHumid,
                )
            }
            Row  {
                SliderQuestion(
                    titleResourceId = R.string.FeedbackLight,
                    value = 0.4f,
                    onValueChange = {},
                    startTextResource = R.string.TooDark,
                    neutralTextResource = R.string.Satisfactory,
                    endTextResource = R.string.TooBright,
                )
            }
        }
    }
}
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

package com.example.compose.jetsurvey.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.theme.JetsurveyTheme


@Composable
@Preview
fun ProfilePagePreview() {
    JetsurveyTheme() {
        Column {
            Row  {
                Text(
                    text = stringResource(R.string.Profile),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.8f)

                )
            }
            Row {
                Image(
                    painter = painterResource(id = R.drawable.lightbulb),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            vertical = 60.dp,
                            horizontal = 86.dp)
                )
            }
            Row  {
                Text(
                    // TO DO:
                    // Pull username of current user
                    text = stringResource(R.string.Username),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.8f)
                )
            }

        }
    }
}

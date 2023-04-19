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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

const val simpleDateFormatPattern = "EEE, MMM d"

class SurveyViewModel(
    private val photoUriManager: PhotoUriManager
) : ViewModel() {

    private val questionOrder: List<SurveyQuestion> = listOf(
        SurveyQuestion.AGE,
        SurveyQuestion.ZIPCODE,
        SurveyQuestion.GENDER,
        SurveyQuestion.IDEAL_BRIGHTNESS,
        SurveyQuestion.IDEAL_TEMPERATURE,

    )

    private var questionIndex = 0

    // ----- Responses exposed as State -----


    private val _userAgeResponse = mutableStateOf<Int?>(null)
    val userAgeResponse: Int?
        get() = _userAgeResponse.value

    fun updateUserAgeResponse(age: Int?) {
        _userAgeResponse.value = age
    }

    private val _zipcodeResponse = mutableStateOf<Int?>(null)
    val zipcodeResponse : Int?
        get() = _zipcodeResponse.value


    private val _genderResponse = mutableStateOf<Int?>(null)
    val genderResponse: Int?
        get() = _genderResponse.value

    private val _idealBrightnessResponse = mutableStateOf<Float?>(null)
    val idealBrightnessResponse: Float?
        get() = _idealBrightnessResponse.value

    private val _idealTemperatureResponse = mutableStateOf<Float?>(null)
    val idealTemperatureResponse: Float?
        get() = _idealBrightnessResponse.value

    // ----- Survey status exposed as State -----

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one question)
     */
    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete
        onSurveyComplete()
    }

    fun onIdealBrightnessResponse(feeling: Float) {
        _idealBrightnessResponse.value = feeling
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onIdealTemperatureResponse(feeling: Float) {
        _idealTemperatureResponse.value = feeling
        _isNextEnabled.value = getIsNextEnabled()
    }


    fun onGenderResponse(response: Int) {
        _genderResponse.value = response
        _isNextEnabled.value = getIsNextEnabled()
    }


    fun onAgeResponse(response: Int) {
        _userAgeResponse.value = response
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onZipcodeResponse(zip: Int) {
        _zipcodeResponse.value = zip
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun getNewSelfieUri() = photoUriManager.buildNewUri()

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.AGE -> _userAgeResponse.value != null
            SurveyQuestion.ZIPCODE -> _zipcodeResponse.value != null
            SurveyQuestion.GENDER -> _genderResponse != null
            SurveyQuestion.IDEAL_BRIGHTNESS ->_idealBrightnessResponse.value != null
            SurveyQuestion.IDEAL_TEMPERATURE ->_idealTemperatureResponse.value != null
        }
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }
}

class SurveyViewModelFactory(
    private val photoUriManager: PhotoUriManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(photoUriManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class SurveyQuestion {
    AGE,
    ZIPCODE,
    GENDER,
    IDEAL_BRIGHTNESS,
    IDEAL_TEMPERATURE,
}

data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)

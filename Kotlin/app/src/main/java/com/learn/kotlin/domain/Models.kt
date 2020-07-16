/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.learn.kotlin.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */

/**
 * Videos represent a story model that can be read.
 */
@Parcelize
data class VideoStoryModel(
    val recordId: Long,
    val title: String,
    val description: String,
    val updated: String,
    val thumbnail: String
) : Parcelable {
    val formattedDate: String
        get() = ZonedDateTime.parse(updated)
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"))
}
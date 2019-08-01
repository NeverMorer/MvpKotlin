/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.religion76.commonlib.paging.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList


/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val loadingState: LiveData<NetworkState>,
        val initLoadingState: LiveData<NetworkState>,
        val errorMessage: LiveData<String>,
        val refresh: () -> Unit,
        val retry: () -> Unit
)
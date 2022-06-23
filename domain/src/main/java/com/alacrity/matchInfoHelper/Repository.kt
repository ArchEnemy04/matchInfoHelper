package com.alacrity.matchInfoHelper

import com.alacrity.matchInfoHelper.entity.Example

interface Repository {

    suspend fun getData(): List<Example>
}
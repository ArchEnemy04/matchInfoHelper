package com.alacrity.matchInfoHelper

import com.alacrity.matchInfoHelper.entity.Example
import com.alacrity.matchInfoHelper.entity.MatchInfo

interface Repository {

    suspend fun getData(): List<MatchInfo>
}
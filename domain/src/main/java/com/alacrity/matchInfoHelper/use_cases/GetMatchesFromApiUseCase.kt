package com.alacrity.matchInfoHelper.use_cases

import com.alacrity.matchInfoHelper.entity.MatchInfo

interface GetMatchesFromApiUseCase {

    suspend operator fun invoke(): List<MatchInfo>

}
package com.alacrity.matchInfoHelper.use_cases

import com.alacrity.matchInfoHelper.Repository
import com.alacrity.matchInfoHelper.entity.MatchInfo
import javax.inject.Inject

class GetMatchesFromApiUseCaseImpl @Inject constructor(
    private val repository: Repository
): GetMatchesFromApiUseCase {

    override suspend fun invoke(): List<MatchInfo> = repository.getData()
}
package com.alacrity.matchInfoHelper.entity

data class MatchInfo(
    val team1: String,
    val team2: String,
    val team1LogoUrl: String,
    val team2LogoUrl: String,
    val bestOdds1: Double,
    val bestOdds2: Double,
    val time: String,
    val tournament: String,
    val date: String,
    val analytics: MatchAnalytics,
    val odds: Odds,
)

data class Odds(
    val bookMaker: List<String>,
    val team1Odds: List<Double>,
    val team2Odds: List<Double>
    )

data class MatchAnalytics(
    val inFavorOf1: List<String>,
    val against1: List<String>,
    val inFavorOf2: List<String>,
    val against2: List<String>,
    val handicap1: Handicap,
    val handicap2: Handicap,
    val mapHandicapTeam1: MapHandicap,
    val mapHandicapTeam2: MapHandicap
)

data class Handicap(val mapScores: Map<String, String>)

data class MapHandicap(
    val avgRoundLostInWins: Double,
    val avgRoundWonInLosses: Double,
    val map1Info: MapInfo,
    val map2Info: MapInfo,
    val map3Info: MapInfo
)

data class MapInfo(val mapName: String,
                   val avgRoundLostInWins: Double,
                   val avgRoundWonInLosses: Double
)


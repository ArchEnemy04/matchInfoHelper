package com.alacrity.matchInfoHelper

import com.alacrity.matchInfoHelper.entity.*
import com.alacrity.matchInfoHelper.utils.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : Repository {


    private lateinit var todoApi: Api

    override suspend fun getData(): List<MatchInfo> {
        todoApi = retrofit.create(Api::class.java)
        val match = MatchInfo(
            team1 = "Ence",
            team2 = "Eternal Fire",
            team1LogoUrl = "https://img-cdn.hltv.org/teamlogo/-X8NoyWC_1gYqUHvZqcpkc.svg?ixlib=java-2.1.0&s=85bb9daa6f846fa097c5942f2565fdb8",
            team2LogoUrl = "https://img-cdn.hltv.org/teamlogo/6NVm0Bx_x0--YcQ9d5qBLR.png?ixlib=java-2.1.0&w=200&s=9a26e7118f368b3f69e9bd479586de8b",
            bestOdds1 = 1.55,
            bestOdds2 = 4.10,
            time = "16:00",
            tournament = "Roobet Cup 2020",
            date = "23rd Jun 2022",
            analytics = MatchAnalytics(
                inFavorOf1 = mutableListOf(
                    "ENCE has better form ranking",
                    "ENCE is the bookmaker favorite with the best odds",
                    "ENCE is better ranked (#3)"
                ),
                against1 = mutableListOf("No insights detected for ENCE."),
                inFavorOf2 = mutableListOf("Eternal Fire has won 4 out of the last 5 matches"),
                against2 = mutableListOf("Eternal Fire is worse ranked (#25)"),
                handicap1 = Handicap(HashMap<String, String>().apply {
                    put("2-0", "47.8%")
                    put("2-1", "26.1%")
                    put("0-2", "17.4%")
                    put("1-2", "8.7%")
                }),
                handicap2 = Handicap(HashMap<String, String>().apply {
                    put("2-0", "47.8%")
                    put("2-1", "26.1%")
                    put("0-2", "17.4%")
                    put("1-2", "8.7%")
                }),
                mapHandicapTeam1 = MapHandicap(
                    avgRoundLostInWins = 10.5,
                    avgRoundWonInLosses = 10.28,
                    map1Info = MapInfo(
                        mapName = "Dust2",
                        avgRoundLostInWins = 13.5,
                        avgRoundWonInLosses = 10.67,
                    ),
                    map2Info = MapInfo(
                        mapName = "Nuke",
                        avgRoundLostInWins = 10.08,
                        avgRoundWonInLosses = 10.67,
                    ),
                    map3Info = MapInfo(
                        mapName = "Vertigo",
                        avgRoundLostInWins = 8.67,
                        avgRoundWonInLosses = 8.5,
                    ),
                ),
                mapHandicapTeam2 = MapHandicap(
                    avgRoundLostInWins = 10.96,
                    avgRoundWonInLosses = 12.10,
                    map1Info = MapInfo(
                        mapName = "Dust2",
                        avgRoundLostInWins = 10.25,
                        avgRoundWonInLosses = 18.0,
                    ),
                    map2Info = MapInfo(
                        mapName = "Nuke",
                        avgRoundLostInWins = 10.86,
                        avgRoundWonInLosses = 14.00,
                    ),
                    map3Info = MapInfo(
                        mapName = "Vertigo",
                        avgRoundLostInWins = 9.75,
                        avgRoundWonInLosses = 9.5,
                    ),
                ),
            ),
            odds = Odds(
                mutableListOf("GGBet", "Betway"),
                mutableListOf(1.48, 1.55),
                mutableListOf(2.57, 2.35)
            )
        )
        return mutableListOf<MatchInfo>().apply {
           for(i in 0 until 10)
               add(match)
        }
        /* return suspendCoroutine { continuation ->
             val api = retrofit.create(Api::class.java)
             val call = api.getData()
             call.enqueue(object : Callback<List<Example>> {
                 override fun onResponse(
                     call: Call<List<Example>>,
                     response: Response<List<Example>>
                 ) {
                     val dataList = response.body()
                     dataList ?: continuation.resumeWithException(NullPointerException())
                         .also { return }
                     continuation.resume(dataList!!)
                 }

                 override fun onFailure(call: Call<List<Example>>, t: Throwable) {
                     continuation.resumeWithException(t)
                 }
             })
         }*/

    }
}
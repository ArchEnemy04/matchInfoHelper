package com.alacrity.matchInfoHelper.ui.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alacrity.matchInfoHelper.R
import com.alacrity.matchInfoHelper.entity.MapHandicap
import com.alacrity.matchInfoHelper.entity.MatchInfo
import com.alacrity.matchInfoHelper.theme.MusicTypography
import com.alacrity.matchInfoHelper.theme.Purple
import com.alacrity.matchInfoHelper.theme.SlightlyGray
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NoNetworkView(onRetryClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Icon(
                Icons.Default.SignalWifiStatusbarConnectedNoInternet4,
                contentDescription = null,
                modifier = Modifier.align(CenterHorizontally)
            )
            Button(
                onClick = { onRetryClick() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("Retry", fontSize = 25.sp)
            }
        }

    }
}

@Composable
fun MatchView(matchInfo: MatchInfo, onClick: (MatchInfo) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(matchInfo) },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GlideImage(
            imageModel = matchInfo.team1LogoUrl,
            contentScale = ContentScale.Crop,
            error = Icons.Filled.Person,
            modifier = Modifier.size(70.dp)
        )
        Text(text = matchInfo.team1, Modifier.align(CenterVertically))
        Text(text = matchInfo.team2, Modifier.align(CenterVertically))
        GlideImage(
            imageModel = matchInfo.team2LogoUrl,
            contentScale = ContentScale.Crop,
            error = Icons.Filled.Person,
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp),
        textAlign = textAlign
    )
}

@Composable
fun OddsTableView(
    team1: String,
    team2: String,
    column1: List<String>,
    column2: List<Double>,
    column3: List<Double>
) {
    val tableData = mutableListOf<Triple<String, Double, Double>>()
    for (i in column1.indices) {
        tableData.add(Triple(column1[i], column2[i], column3[i]))
    }
    val column1Weight = .26f
    val column2Weight = .37f
    val column3Weight = .37f

    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(color = SlightlyGray)) {
                TableCell(text = "", weight = column1Weight)
                TableCell(text = team1, weight = column2Weight)
                TableCell(text = team2, weight = column3Weight)
            }
        }
        items(tableData) {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = it.first, weight = column1Weight)
                TableCell(text = it.second.toString(), weight = column2Weight)
                TableCell(text = it.third.toString(), weight = column3Weight)
            }
        }
    }
}

@Composable
fun HandicapCard(matchInfo: MatchInfo, teamFirst: Boolean, modifier: Modifier) {
    val team = if (teamFirst) matchInfo.team1 else matchInfo.team2
    val stats = if (teamFirst) matchInfo.analytics.handicap1.mapScores else
        matchInfo.analytics.handicap2.mapScores
    Card(
        modifier = modifier
            .padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray,
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text(text = team, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            stats.forEach { (k, v) ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween) {
                    Text(text = k)
                    Text(text = v)
                }
            }
        }
    }
}

@Composable
fun MapHandicap(matchInfo: MatchInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MapHandicapCard(matchInfo = matchInfo, teamFirst = true, modifier = Modifier)
        MapHandicapCard(matchInfo = matchInfo, teamFirst = false, modifier = Modifier)
    }
}

@Composable
fun MapHandicapCard(matchInfo: MatchInfo, teamFirst: Boolean, modifier: Modifier) {
    val team = if (teamFirst) matchInfo.team1 else matchInfo.team2
    val mapHandicap = if (teamFirst) matchInfo.analytics.mapHandicapTeam1
    else matchInfo.analytics.mapHandicapTeam2

    Card(
        modifier = modifier
            .padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray,
    ) {
        Text(
            text = "$team Map Handicap",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OverallDataTab(mapHandicap.avgRoundWonInLosses, mapHandicap.avgRoundLostInWins)
        MapDataTab(mapHandicap)
    }
}

@Composable
fun OverallDataTab(avgInWins: Double, avgInLost: Double) {
    Card(
        modifier = Modifier
            .padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = avgInWins.toString(),
                    style = MusicTypography.h2,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Avg. rounds lost in wins",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = avgInLost.toString(),
                    style = MusicTypography.h2,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Avg. rounds won in losses",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MapDataTab(mapHandicap: MapHandicap) {

}
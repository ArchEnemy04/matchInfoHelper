package com.alacrity.matchInfoHelper.ui.main.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alacrity.matchInfoHelper.entity.Handicap
import com.alacrity.matchInfoHelper.entity.MatchInfo
import com.alacrity.matchInfoHelper.theme.Purple
import com.alacrity.matchInfoHelper.theme.SlightlyGray
import com.alacrity.matchInfoHelper.util.getScreenSize
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ScreenWithItems(list: List<MatchInfo>, onClick: (MatchInfo) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
                MatchView(matchInfo = item) { onClick(it) }
            }
        }
    }
}

@Composable
fun DetailMatchScreen(info: MatchInfo) {
    val contentHeight = getScreenSize<Float>().second * 0.8
    val adHeight = getScreenSize<Float>().second * 0.2
    Column(modifier = Modifier.fillMaxSize().height(contentHeight.dp)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(1) {
                Header(matchInfo = info)
                Analytics(matchInfo = info)
                OddsTab(matchInfo = info)
                HandicapTab(matchInfo = info)
            }
        }
        AdvertisementTab(modifier = Modifier.height(adHeight.dp))
    }
}

@Composable
fun Header(matchInfo: MatchInfo) {
    Card(
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.weight(1f)) {
                GlideImage(
                    imageModel = matchInfo.team1LogoUrl,
                    contentScale = ContentScale.Crop,
                    error = Icons.Filled.Person,
                    modifier = Modifier
                        .size(60.dp)
                        .align(CenterHorizontally)
                )
                Text(
                    text = matchInfo.team1,
                    fontSize = 12.sp,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }
            InfoView(matchInfo = matchInfo, modifier = Modifier.weight(2f))
            Column(modifier = Modifier.weight(1f)) {
                GlideImage(
                    imageModel = matchInfo.team2LogoUrl,
                    contentScale = ContentScale.Crop,
                    error = Icons.Filled.Person,
                    modifier = Modifier
                        .size(60.dp)
                        .align(CenterHorizontally)
                )
                Text(
                    text = matchInfo.team2,
                    fontSize = 12.sp,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }
        }
    }
}


@Composable
fun Analytics(matchInfo: MatchInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Analytics",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            ProsAndConsCard(
                matchInfo = matchInfo,
                teamFirst = true,
                modifier = Modifier.weight(1f)
            )
            ProsAndConsCard(
                matchInfo = matchInfo,
                teamFirst = false,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun ProsAndConsCard(matchInfo: MatchInfo, teamFirst: Boolean, modifier: Modifier) {
    val team = if (teamFirst) matchInfo.team1 else matchInfo.team2
    val pros =
        (if (teamFirst) matchInfo.analytics.inFavorOf1 else matchInfo.analytics.inFavorOf2) as MutableList<String>
    val cons =
        (if (teamFirst) matchInfo.analytics.against1 else matchInfo.analytics.against2) as MutableList<String>

    Card(
        modifier = modifier.padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            SmallTextView(text = team)
            SmallTextView(text = "PROS/CONS")
            SmallTextView(text = "This speaks in favor of $team...")
            pros.forEach {
                SmallTextView(text = it)
            }
            SmallTextView(text = "This speaks against $team...")
            cons.forEach {
                SmallTextView(text = it)
            }
        }
    }
}

@Composable
fun InfoView(matchInfo: MatchInfo, modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = matchInfo.time,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = matchInfo.tournament,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = matchInfo.date,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OddsTab(matchInfo: MatchInfo) {
    Card(
        modifier = Modifier.padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = SlightlyGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = "${matchInfo.team1} vs ${matchInfo.team2} odds",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            /*OddsTableView(
                team1 = matchInfo.team1,
                team2 = matchInfo.team2,
                column1 = matchInfo.odds.bookMaker,
                column2 = matchInfo.odds.team1Odds,
                column3 = matchInfo.odds.team2Odds
            )*/
        }
    }
}

@Composable
fun AdvertisementTab(modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .height(100.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Purple,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
            Text(
                "Тут будет ваша реклама",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HandicapTab(matchInfo: MatchInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Handicap data, past 3 months", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth()) {
            HandicapCard(matchInfo, teamFirst = true, modifier = Modifier.weight(1f))
            HandicapCard(matchInfo, teamFirst = false, modifier = Modifier.weight(1f))
        }
        MapHandicap(matchInfo)
    }
}


@Composable
fun SmallTextView(text: String) {
    Text(text = text, fontSize = 14.sp)
}


package com.mahadiks.basketballapp.ui.components

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.mahadiks.basketballapp.models.HomeTeam
import com.mahadiks.basketballapp.models.TeamsSchedule
import com.mahadiks.basketballapp.models.VisitedTeam

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameCard(
    game: TeamsSchedule,
) {
    val appTeamId = "1610612748"
    val homeTeam = game.homeTeam
    val awayTeam = game.visitedTeam
    val isAppTeamHome = game.homeTeam?.teamId == appTeamId
    if (isAppTeamHome) awayTeam else homeTeam
    val displayText = if (isAppTeamHome) "vs" else "@"
    val venue = if (isAppTeamHome) "Home" else "AWAY"
    val localDateTime = game.gameDate.plus(" | ${game.gameTime}")
    //val primaryColor = Color(android.graphics.Color.parseColor(teams[game.homeTid]?.primaryColor ?: "#FFFFFF"))*/

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        // colors = CardDefaults.cardColors(containerColor = primaryColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$venue | $localDateTime ",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeTeamColumn(team = homeTeam)
                Text(
                    text = homeTeam?.teamScore.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W900
                )
                Text(text = displayText, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = awayTeam?.teamScore.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W900
                )
                VisitedTeamColumn(team = awayTeam)
            }
            StatusBadge(game.gameStatus)
            if ((game.buyTicket.isNotEmpty() || game.buyTicket.isNotBlank()) && game.gameStatus != 3) {
                BuyTicketButton(game.buyTicket)
            }

        }
    }
}

@Composable
fun HomeTeamColumn(team: HomeTeam?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = team?.teamLogo,
            contentDescription = team?.teamShortName,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = team?.teamShortName.toString(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.W900
        )
    }
}

@Composable
fun VisitedTeamColumn(team: VisitedTeam?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = team?.teamLogo,
            contentDescription = team?.teamShortName,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = team?.teamShortName.toString(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.W900
        )
    }
}


@Composable
fun StatusBadge(status: Int) {
    val (text, color) = when (status) {
        1 -> "Upcoming" to Color(0xFF055005)
        2 -> "LIVE" to Color.Red
        3 -> "Final" to Color.Black
        else -> "Unknown" to Color.Black
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .background(color, RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 1.dp)
        ) {
            Text(text = text, color = Color.White, fontWeight = FontWeight.W900, fontSize = 12.sp)
        }
    }

}

@Composable
fun BuyTicketButton(buyTicket: String) {

    val context = LocalContext.current

    val fullUrl = "https://www.ticketmaster.com/event/$buyTicket"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, fullUrl.toUri())
                context.startActivity(intent)
            }
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 1.dp), contentAlignment = Alignment.Center) {
            Text(
                text = "Buy Ticket on TicketMaster",
                fontWeight = FontWeight.W900,
                fontSize = 14.sp
            )
        }
    }

}

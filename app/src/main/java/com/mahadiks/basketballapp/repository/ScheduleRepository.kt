package com.mahadiks.basketballapp.repository


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.mahadiks.basketballapp.models.DataS
import com.mahadiks.basketballapp.models.DataT
import com.mahadiks.basketballapp.models.HomeTeam
import com.mahadiks.basketballapp.models.TeamsSchedule
import com.mahadiks.basketballapp.models.VisitedTeam
import com.mahadiks.basketballapp.util.formatGameDate
import com.mahadiks.basketballapp.util.formatGameYearAndMonth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTeamSchedule(): List<TeamsSchedule> {
        delay(3000L)
        //read the Json files
        val scheduleData = parseJsonAsset<DataS>("Schedule.json")
        val teamsData = parseJsonAsset<DataT>("teams.json")

        val teamsMap = teamsData.data.teamsList.associateBy { it.teamId }
        val teamsScheduleList = scheduleData.data.listSchedule.mapNotNull { scheduleItem ->

            val homeTeamInfo = teamsMap[scheduleItem.homeTeam.teamId]
            val visitedTeamInfo = teamsMap[scheduleItem.visitedTeam.teamId]

            if (homeTeamInfo == null || visitedTeamInfo == null) return@mapNotNull null

            TeamsSchedule(
                homeTeam = HomeTeam(
                    teamFullName = homeTeamInfo.teamFullName,
                    teamId = homeTeamInfo.teamId,
                    teamLogo = homeTeamInfo.teamLogoUrl,
                    teamShortName = homeTeamInfo.teamShortName,
                    teamScore = scheduleItem.homeTeam.teamScore
                ),
                visitedTeam = VisitedTeam(
                    teamFullName = visitedTeamInfo.teamFullName,
                    teamId = visitedTeamInfo.teamId,
                    teamLogo = visitedTeamInfo.teamLogoUrl,
                    teamShortName = visitedTeamInfo.teamShortName,
                    teamScore = scheduleItem.visitedTeam.teamScore
                ),
                gameDate = formatGameDate(scheduleItem.gameTimeAndDate),
                gameStatus = scheduleItem.gameStatus,
                gameTime = scheduleItem.matchTime.replace(" ET", "").uppercase(Locale.ENGLISH),
                gameYearAndMonth = formatGameYearAndMonth(scheduleItem.gameTimeAndDate),
                gameVenue = scheduleItem.arenaCityName,
                buyTicket = scheduleItem.buyTicket.orEmpty()
            )
        }

        return teamsScheduleList
    }

    private inline fun <reified T> parseJsonAsset(fileName: String): T {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Gson().fromJson(json, T::class.java)
    }
}
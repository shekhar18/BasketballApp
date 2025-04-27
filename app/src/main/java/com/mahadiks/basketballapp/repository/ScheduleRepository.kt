package com.mahadiks.basketballapp.repository


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toUpperCase
import com.google.gson.Gson
import com.mahadiks.basketballapp.models.DataS
import com.mahadiks.basketballapp.models.DataT
import com.mahadiks.basketballapp.models.HomeTeam
import com.mahadiks.basketballapp.models.TeamsSchedule
import com.mahadiks.basketballapp.models.VisitedTeam
import com.mahadiks.basketballapp.util.formatGameDate
import com.mahadiks.basketballapp.util.formatGameYearAndMonth
import com.mahadiks.basketballapp.util.parseTimeString
import kotlinx.coroutines.delay
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


class ScheduleRepository(val context: Context) {


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTeamSchedule(): List<TeamsSchedule> {
        delay(1000L)
        val scheduleJsonObject =
            context.assets.open("Schedule.json").bufferedReader().use { it.readText() }
        val teamsJsonObject =
            context.assets.open("teams.json").bufferedReader().use { it.readText() }


        val schedule = Gson().fromJson(scheduleJsonObject, DataS::class.java)
        val teams = Gson().fromJson(teamsJsonObject, DataT::class.java)

        val teamsScheduleList = mutableListOf<TeamsSchedule>()
        for (scheduleItem in schedule.data.listSchedule) {
            var homeTeam: HomeTeam? = null
            var visitedTeam: VisitedTeam? = null
            for (teamsItem in teams.data.teamsList) {
                if (scheduleItem.homeTeam.teamId == teamsItem.teamId) {
                    homeTeam = HomeTeam(
                        teamFullName = teamsItem.teamFullName,
                        teamId = teamsItem.teamId,
                        teamLogo = teamsItem.teamLogoUrl,
                        teamShortName = teamsItem.teamShortName,
                        teamScore = scheduleItem.homeTeam.teamScore
                    )
                }
            }
            for (teamsItem in teams.data.teamsList) {

                if (scheduleItem.visitedTeam.teamId == teamsItem.teamId) {
                    visitedTeam = VisitedTeam(
                        teamFullName = teamsItem.teamFullName,
                        teamId = teamsItem.teamId,
                        teamLogo = teamsItem.teamLogoUrl,
                        teamShortName = teamsItem.teamShortName,
                        teamScore = scheduleItem.visitedTeam.teamScore
                    )
                }
            }
            val teamsSchedule = TeamsSchedule(
                homeTeam = homeTeam,
                visitedTeam = visitedTeam,
                gameDate = formatGameDate(scheduleItem.gameTimeAndDate) ,
                gameStatus = scheduleItem.gameStatus,
                gameTime = scheduleItem.matchTime.replace(" ET", "").toUpperCase(Locale.ENGLISH),
                gameYearAndMonth = formatGameYearAndMonth(scheduleItem.gameTimeAndDate),
                gameVenue = scheduleItem.arenaCityName,
                buyTicket = scheduleItem.buyTicket ?: ""
            )
            teamsScheduleList.add(teamsSchedule)
        }
        return teamsScheduleList
    }
}
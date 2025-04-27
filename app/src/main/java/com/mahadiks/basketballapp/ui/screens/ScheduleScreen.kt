package com.mahadiks.basketballapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mahadiks.basketballapp.ui.components.GameCard
import com.mahadiks.basketballapp.viewmodel.ScheduleViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(modifier: Modifier, viewModel: ScheduleViewModel) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        } else {
            val scheduleList = viewModel.teamsSchedule.observeAsState(initial = emptyList())
            val list = scheduleList.value.toList()
            val groupByTeamSchedule = list.groupBy { it.gameYearAndMonth }
            LazyColumn {
                groupByTeamSchedule.forEach { (month, games) ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "Up Arrow Icon",
                                modifier = Modifier.size(25.dp),

                                )
                            Text(
                                text = month, modifier = Modifier, fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Down Arrow Icon",
                                modifier = Modifier.size(25.dp),

                                )
                        }
                    }
                    items(games) { games ->
                        GameCard(games)
                    }
                }
            }

        }
    }

}

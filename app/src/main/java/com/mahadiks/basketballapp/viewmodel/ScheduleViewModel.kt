package com.mahadiks.basketballapp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahadiks.basketballapp.models.TeamsSchedule
import com.mahadiks.basketballapp.repository.ScheduleRepository
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val repository: ScheduleRepository =
        ScheduleRepository(getApplication<Application>().applicationContext)

    private val _teamScheduleList = MutableLiveData<List<TeamsSchedule>>()
    val teamsSchedule: LiveData<List<TeamsSchedule>> = _teamScheduleList

    var isLoading by mutableStateOf(true)



    init {
        viewModelScope.launch {
            val schedule = repository.getTeamSchedule()
            _teamScheduleList.postValue(schedule)
            isLoading = false
        }
    }
}
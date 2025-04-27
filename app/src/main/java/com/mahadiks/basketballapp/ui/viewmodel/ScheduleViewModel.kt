package com.mahadiks.basketballapp.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahadiks.basketballapp.models.TeamsSchedule
import com.mahadiks.basketballapp.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _teamScheduleList = MutableLiveData<List<TeamsSchedule>>()
    val teamsSchedule: LiveData<List<TeamsSchedule>> = _teamScheduleList

    var isLoading = mutableStateOf(true)
        private set

    var isError = mutableStateOf(false)
        private set

    init {
        fetchTeamSchedule()
    }

    private fun fetchTeamSchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val schedule = repository.getTeamSchedule()
                _teamScheduleList.postValue(schedule)
            } catch (e: Exception) {
                isError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }

    fun retry() {
        isLoading.value = true
        fetchTeamSchedule()
    }
}
package com.example.gymapplicationalpha.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.daos.WorkoutExerciseSetDao
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet
import com.example.gymapplicationalpha.data.events.WorkoutExerciseSetEvent
import com.example.gymapplicationalpha.data.states.WorkoutExerciseSetState
import com.example.gymapplicationalpha.data.states.WorkoutState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutExerciseSetViewModel (
    private val workoutExerciseSetDao: WorkoutExerciseSetDao
): ViewModel() {

    private val _workoutId = MutableStateFlow<Int?>(null)
    private val _exerciseName = MutableStateFlow<String?>(null)
    private val _sortType = MutableStateFlow(SortType.SET_NUMBER)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _sets = combine(_workoutId, _exerciseName, _sortType) { workoutId, exerciseName, sortType ->
        if (workoutId != null && exerciseName != null) {
            when (sortType) {
                SortType.SET_NUMBER -> workoutExerciseSetDao.getSetsForExerciseInWorkout(workoutId, exerciseName)
                else -> workoutExerciseSetDao.getSetsForExerciseInWorkout(workoutId, exerciseName)
            }
        } else {
            flowOf(emptyList<WorkoutExerciseSet>())
        }
    }
        .flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(WorkoutExerciseSetState())

    val state = combine(_state, _sortType, _sets) { state, sortType, sets ->
        state.copy(
            sets = sets,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), WorkoutExerciseSetState())

    fun onEvent(event: WorkoutExerciseSetEvent) {
        when(event) {
            is WorkoutExerciseSetEvent.deleteSet -> {
                viewModelScope.launch {
                    workoutExerciseSetDao.deleteSet(event.workoutExerciseSet)
                }
            }
            WorkoutExerciseSetEvent.saveSet -> {
                val setNumber = state.value.setNumber
                val repNumber = state.value.repNumber
                val weight = state.value.weight
                //if (setNumber) return

                val set = WorkoutExerciseSet(setNumber = setNumber, repNumber = repNumber, weight = weight)
                viewModelScope.launch {
                    workoutExerciseSetDao.upsertSet(set)
                }
            }
            is WorkoutExerciseSetEvent.sortSet -> {
                _sortType.value = event.sortType
            }
        }
    }
}
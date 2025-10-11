package com.example.gymapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel (
    private val dao: WorkoutDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DATE)
    private val _workouts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.WORKOUT_TYPE -> dao.getWorkoutsByType()
                SortType.DATE -> dao.getWorkoutsByDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(WorkoutState())
    val state = combine(_state, _sortType, _workouts) { state, sortType, workouts ->
        state.copy(
            workouts = workouts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutState())

    fun onEvent(event: WorkoutEvent) {
        when(event) {
            is WorkoutEvent.DeleteWorkout -> {
                viewModelScope.launch {
                    dao.deleteWorkout(event.workout)
                }
            }
            WorkoutEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingWorkout = false
                ) }
            }
            WorkoutEvent.SaveWorkout -> {
                val workoutType = state.value.workoutType
                val date = state.value.date

                if(workoutType.isBlank() || date.isBlank()) {
                    return
                }

                val workout = Workout(
                    workoutType = workoutType,
                    date = date
                )
                viewModelScope.launch {
                    dao.upsertWorkout(workout)
                }
                _state.update { it.copy(
                    isAddingWorkout = false,
                    workoutType = "",
                    date = "",
                ) }
            }
            WorkoutEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingWorkout = true
                ) }
            }
            is WorkoutEvent.SortWorkout -> {
                _sortType.value = event.SortType
            }
            is WorkoutEvent.setWorkoutDate -> {
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is WorkoutEvent.setWorkoutType -> {
                _state.update { it.copy(
                    workoutType = event.workoutType
                ) }
            }
        }
    }
}
package com.example.gymapplicationalpha.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.daos.WorkoutDao
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.events.WorkoutEvent
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef
import com.example.gymapplicationalpha.data.states.WorkoutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val workoutDao: WorkoutDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.WORKOUT_DATE)

    private val _workouts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.WORKOUT_DATE -> workoutDao.getWorkoutsByDate()
                else -> workoutDao.getWorkoutsByDate()
            }
        }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(WorkoutState())

    val state = combine(_state, _sortType, _workouts) { state, sortType, workouts ->
        state.copy(
            workouts = workouts,
            sortType = sortType
        )
    }
    .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), WorkoutState())


    fun onEvent(event: WorkoutEvent) {
        when(event) {
            is WorkoutEvent.AddWorkoutExerciseCrossRef -> {
                viewModelScope.launch {
                    val crossRef = WorkoutExerciseCrossRef(
                        workoutSession = event.workoutSession,
                        exerciseName = event.exerciseName
                    )
                    workoutDao.upsertWorkoutExerciseCrossRef(crossRef)
                }
            }

            is WorkoutEvent.DeleteWorkoutExerciseCrossRef -> {
                viewModelScope.launch {
                    val crossRef = WorkoutExerciseCrossRef(
                        workoutSession = event.workoutSession,
                        exerciseName = event.exerciseName
                    )
                    workoutDao.deleteWorkoutExerciseCrossRef(crossRef)
                }
            }

            is WorkoutEvent.SortWorkout -> {
                _sortType.value = event.SortType
            }
            is WorkoutEvent.DeleteWorkout -> {
                viewModelScope.launch {
                    workoutDao.deleteWorkout(event.workout)
                }
            }
            WorkoutEvent.SaveWorkout -> {
                val date = state.value.date
                val type = state.value.type
                val description = state.value.description
                if (date.isBlank()) return

                val workout = Workout(date = date, workoutType = type, description = description)
                viewModelScope.launch {
                    workoutDao.upsertWorkout(workout)
                }
            }

            is WorkoutEvent.setWorkoutDate -> {
                _state.update { it.copy(
                    date = event.workoutDate
                ) }
            }

            is WorkoutEvent.setWorkoutType -> {
                _state.update { it.copy(
                    type = event.workoutType
                ) }
            }

            is WorkoutEvent.setWorkoutDescription -> {
                _state.update { it.copy(
                    description = event.workoutDescription
                ) }
            }
        }
    }
}
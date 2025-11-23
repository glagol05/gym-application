package com.example.gymapplicationalpha.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.daos.ExerciseDao
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.events.ExerciseEvent
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef
import com.example.gymapplicationalpha.data.states.ExerciseState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val exerciseDao: ExerciseDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.EXERCISE_NAME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _exercises = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.EXERCISE_NAME -> exerciseDao.getExercisesByName()
                else -> exerciseDao.getExercisesByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    private val _state = MutableStateFlow(ExerciseState())
    val state = combine(_state, _sortType, _exercises) { state, sortType, exercises ->
        state.copy(
            exercises = exercises,
            sortType = sortType
        )
    }
    .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), ExerciseState())

    fun onEvent(event: ExerciseEvent) {
        when(event) {
            is ExerciseEvent.DeleteExercise -> {
                viewModelScope.launch {
                    exerciseDao.deleteExercise(event.exercise)
                }
            }

            is ExerciseEvent.AddExerciseToWorkout -> {
                viewModelScope.launch {
                    val crossRef = WorkoutExerciseCrossRef(
                        workoutSession = event.workoutSession,
                        exerciseId = event.exerciseId,
                        addedOrder = System.currentTimeMillis()
                    )
                    exerciseDao.addExerciseToWorkout(crossRef)
                }
            }

            is ExerciseEvent.RemoveExerciseFromWorkout -> {
                viewModelScope.launch {
                    val crossRef = WorkoutExerciseCrossRef(
                        workoutSession = event.workoutSession,
                        exerciseId = event.exerciseId,
                        addedOrder = System.currentTimeMillis()
                    )
                    exerciseDao.removeExerciseFromWorkout(crossRef)
                }
            }

            ExerciseEvent.SaveExercise -> {
                val name = state.value.name
                val type = state.value.type
                val image = state.value.imageName
                if(name.isBlank()) return

                val exercise = Exercise(exerciseName = name, exerciseType = type, imageName = image)
                viewModelScope.launch {
                    exerciseDao.upsertExercise(exercise)
                }
                _state.update { it.copy(name = "", type = "", imageName = "") }
            }
            is ExerciseEvent.SortExercise -> {
                _sortType.value = event.SortType
            }

            is ExerciseEvent.setExerciseImage -> {
                _state.update { it.copy(
                    imageName = event.imageName
                ) }
            }
            is ExerciseEvent.setExerciseName -> {
                _state.update { it.copy(
                    name = event.exerciseName
                ) }
            }
            is ExerciseEvent.setExerciseType -> {
                _state.update { it.copy(
                    type = event.exerciseType
                ) }
            }
        }
    }
}
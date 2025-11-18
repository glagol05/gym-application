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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutExerciseSetViewModel(
    private val workoutExerciseSetDao: WorkoutExerciseSetDao
) : ViewModel() {

    private val _workoutId = MutableStateFlow<Int?>(null)
    private val _exerciseId = MutableStateFlow<Int?>(null)
    private val _sortType = MutableStateFlow(SortType.SET_NUMBER)

    private val _state = MutableStateFlow(WorkoutExerciseSetState())

    fun load(workoutId: Int, exerciseId: Int) {
        _workoutId.value = workoutId
        _exerciseId.value = exerciseId

        _state.value = _state.value.copy(
            workoutId = workoutId,
            exerciseId = exerciseId
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _sets = combine(_workoutId, _exerciseId, _sortType) { workoutId, exerciseId, sortType ->
        if (workoutId != null && exerciseId != null) {
            workoutExerciseSetDao.getSetsForExerciseInWorkout(workoutId, exerciseId)
        } else {
            flowOf(emptyList<WorkoutExerciseSet>())
        }
    }.flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<WorkoutExerciseSet>())

    val state = combine(_state, _sortType, _sets) { state, sortType, sets ->
        state.copy(
            sets = sets,
            sortType = sortType
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutExerciseSetState())

    fun updateSetNumber(v: Int) { _state.value = _state.value.copy(setNumber = v) }
    fun updateRepNumber(v: Int) { _state.value = _state.value.copy(repNumber = v) }
    fun updateWeight(v: Float) { _state.value = _state.value.copy(weight = v) }

    fun getSetsForExerciseInWorkout(workoutId: Int, exerciseId: Int) =
        workoutExerciseSetDao.getSetsForExerciseInWorkout(workoutId, exerciseId)

    fun onEvent(event: WorkoutExerciseSetEvent) {
        when (event) {
            is WorkoutExerciseSetEvent.deleteSet -> {
                viewModelScope.launch {
                    workoutExerciseSetDao.deleteSet(event.workoutExerciseSet)
                }
            }

            is WorkoutExerciseSetEvent.deleteAlLSetsByWorkoutId -> {
                viewModelScope.launch {
                    workoutExerciseSetDao.deleteAllSetsByWorkoutId(event.workoutId)
                }
            }

            is WorkoutExerciseSetEvent.saveSet -> {
                val set = WorkoutExerciseSet(
                    workoutId = event.workoutSession,
                    exerciseId = event.exerciseId,
                    setNumber = event.setNumber,
                    repNumber = event.repNumber,
                    weight = event.weight.toFloatOrNull() ?: 0f
                )
                viewModelScope.launch {
                    workoutExerciseSetDao.upsertSet(set)
                }

                _state.value = _state.value.copy(
                    setNumber = 0,
                    repNumber = 0,
                    weight = 0f
                )
            }

            is WorkoutExerciseSetEvent.sortSet -> {
                _sortType.value = event.sortType
            }
        }
    }
}

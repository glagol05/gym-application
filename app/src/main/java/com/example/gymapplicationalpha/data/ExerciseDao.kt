package com.example.gymapplicationalpha.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Insert
    suspend fun addExerciseToWorkout(crossRef: WorkoutExerciseCrossRef)

    @Delete
    suspend fun removeExerciseFromWorkout(crossRef: WorkoutExerciseCrossRef)

    //@Query("DELETE FROM WorkoutExerciseCrossRef WHERE workoutSession = :workoutSession AND exerciseName = :exerciseName")
    //suspend fun removeExerciseFromWorkout(workoutSession: Int, exerciseName: String)

    @Query("SELECT * FROM exercises ORDER BY exerciseName ASC")
    fun getExercisesByName(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises ORDER BY exerciseType ASC")
    fun getExercisesByType(): Flow<List<Exercise>>

}
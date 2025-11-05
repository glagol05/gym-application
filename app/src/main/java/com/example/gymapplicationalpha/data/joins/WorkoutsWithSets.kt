import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef

data class WorkoutWithSets(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutSession",
        entityColumn = "exerciseName",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )
    val exercises: List<Exercise>,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "exerciseName"
    )
    val sets: List<WorkoutExerciseSet>
)

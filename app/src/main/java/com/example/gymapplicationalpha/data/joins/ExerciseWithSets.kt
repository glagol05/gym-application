import androidx.room.Embedded
import androidx.room.Relation
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

data class ExerciseWithSets(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "exerciseName"
    )
    val sets: List<WorkoutExerciseSet>
)
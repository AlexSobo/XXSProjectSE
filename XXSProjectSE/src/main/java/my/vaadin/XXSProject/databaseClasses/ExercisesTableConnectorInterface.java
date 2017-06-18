package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import my.vaadin.XXSProject.databaseEntities.Exercise;

public interface ExercisesTableConnectorInterface {
	public List<Exercise> getExercicesForUsersWorkoutPlan(String username, String workoutName);

	public void addExerciseToPlan(Exercise newExercise);

	public void deleteExerciseForUsernameAndPlan(String username, String planName, String exerciseName);

	public void deleteExercisesForUsernameAndPlan(String username, String planName);
}

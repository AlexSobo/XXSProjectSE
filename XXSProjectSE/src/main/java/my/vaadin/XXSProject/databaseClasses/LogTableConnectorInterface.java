package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import my.vaadin.XXSProject.databaseEntities.Log;

public interface LogTableConnectorInterface {
	public List<Log> getLogsForUsernameWorkoutPlanExercise(String username, String workoutPlanName,
			String exerciveName);

	public void addLog(Log logToAdd);

	public void deleteLogsForUsernameAndWorkOutPlan(String username, String workoutPlan);

	public void deleteLogsForUsernameAndWorkoutPlanAndExercise(String username, String workoutPlan, String exercise);
}

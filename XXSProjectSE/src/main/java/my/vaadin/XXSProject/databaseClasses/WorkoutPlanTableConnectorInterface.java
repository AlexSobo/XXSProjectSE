package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import my.vaadin.XXSProject.databaseEntities.WorkoutPlan;

public interface WorkoutPlanTableConnectorInterface {
	public List<WorkoutPlan> getWorkoutplansForUsername(String username);

	public void addWorkoutPlanForUsername(WorkoutPlan newWorkout);

	public void deleteWorkoutPlanForUserName(String username, String planName);
	

}

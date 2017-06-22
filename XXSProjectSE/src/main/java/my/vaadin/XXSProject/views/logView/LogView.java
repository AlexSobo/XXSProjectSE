package my.vaadin.XXSProject.views.logView;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseClasses.ExercisesTableConnector;
import my.vaadin.XXSProject.databaseClasses.WorkoutPlanTableConnector;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.WorkoutPlan;

public class LogView extends CssLayout implements View {

	public static final String VIEW_NAME = "Logs";
	private MyUI parentUI;

	private NativeSelect<WorkoutPlan> selectPlan;
	private LogShowDisplay logShowDisplay;
	private LogShowChart logShowChart;
	private ArrayList<LogAddDisplay> currentLogAddDisplays;

	public LogView(MyUI ui) {
		this.parentUI = ui;
		setSizeFull();
		addStyleName("about-content");

		VerticalLayout centeringLayout = new VerticalLayout();
		centeringLayout.setMargin(false);
		centeringLayout.setSpacing(false);
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout = this.buildUI();
		VerticalLayout secondLayout = new VerticalLayout();
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(contentLayout);
		centeringLayout.addComponent(secondLayout);
		centeringLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
		this.addComponent(centeringLayout);
	}

	private VerticalLayout buildUI() {
		VerticalLayout contentLayout = new VerticalLayout();
		addStyleName("about-view");

		logShowDisplay = new LogShowDisplay();
		contentLayout.addComponent(logShowDisplay);

		this.logShowChart = new LogShowChart();
		contentLayout.addComponent(logShowChart);

		selectPlan = new NativeSelect<>("Logs hinzufügen");
		selectPlan.setEmptySelectionAllowed(false);
		contentLayout.addComponent(this.selectPlan);

		// Array, in dem alle aktuellen LogAddDisplays gespeichert werden
		this.currentLogAddDisplays = new ArrayList<>();

		// Action-Listener - Workoutplan wird ausgewählt
		this.selectPlan.addSelectionListener(e -> {
			this.selectedWorkoutPlanChanged();
		});

		return contentLayout;
	}

	// LogShowDisplay aktualisieren
	public void updateLogShow(Exercise exerciseToShow) {
		this.logShowDisplay.showLogsForExersice(exerciseToShow);
		this.logShowChart.showLogsForExercise(exerciseToShow);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		// Daten für Plan-Select laden
		WorkoutPlanTableConnector workoutPlanProvider = new WorkoutPlanTableConnector();
		List<WorkoutPlan> existingWorkouts = workoutPlanProvider
				.getWorkoutplansForUsername(this.getParentUI().getLoggedInUsername());

		if (existingWorkouts.size() != 0) {
			this.selectPlan.setItems(existingWorkouts);
		}

	}

	// WorkoutPlan wird ausgewählt
	private void selectedWorkoutPlanChanged() {
		WorkoutPlan selectedPlan = this.selectPlan.getSelectedItem().get();

		ExercisesTableConnector exerciseProvider = new ExercisesTableConnector();
		List<Exercise> existingExercisesForPlan = exerciseProvider
				.getExercicesForUsersWorkoutPlan(this.getParentUI().getLoggedInUsername(), selectedPlan.getName());

		// Check, ob es Übungen für ausgewählten Plan gibt
		if (existingExercisesForPlan.size() != 0) {
			// Alte Logs entfernen
			for (LogAddDisplay ld : currentLogAddDisplays) {
				this.removeComponent(ld);
			}
			this.currentLogAddDisplays.clear();

			// //Neuen LogView hinzufügen
			for (Exercise ex : existingExercisesForPlan) {
				this.currentLogAddDisplays.add(new LogAddDisplay(this, ex));
			}
			for (LogAddDisplay ld : currentLogAddDisplays) {
				this.addComponent(ld);
			}

		} else {
			// Alte Logs entfernen
			for (LogAddDisplay ld : currentLogAddDisplays) {
				this.removeComponent(ld);
			}
			this.currentLogAddDisplays.clear();

			Notification.show("Keine Übungen für Plan", "", Notification.Type.HUMANIZED_MESSAGE);
		}
	}

	private MyUI getParentUI() {
		return parentUI;
	}

}

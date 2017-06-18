package my.vaadin.XXSProject.views.overviewView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseClasses.ExercisesTableConnector;
import my.vaadin.XXSProject.databaseClasses.LogTableConnector;
import my.vaadin.XXSProject.databaseClasses.WorkoutPlanTableConnector;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.WorkoutPlan;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class OverviewView extends CssLayout implements View {
	private MyUI parentUI;
	
	private Grid<WorkoutPlan> gridWorkouts;
	private List<WorkoutPlan> existingWorkouts;
	private TextField tfAddWorkoutPlanName;
	private TextField tfAddWorkoutPlanDesc;
	private Button btnAddWorkoutPlan;
	private Button btnRmvWorkoutPlan;

	private Grid<Exercise> gridExercises;
	private List<Exercise> existingExercises;
	private TextField tfAddExerciseName;
	private TextField tfAddExerciseTargReps;
	private TextField tfAddExerciseTargSets;
	private TextField tfAddExerciseTargWeights;
	private Button btnAddExercise;
	private Button btnRmvExercise;
	
	private WorkoutPlan newWorkout;
	private WorkoutPlanTableConnector workoutPlanCreater;

    public static final String VIEW_NAME = "Übersicht";

    public OverviewView(MyUI ui) {
    	this.parentUI = ui;
        setSizeFull();
        addStyleName("crud-view");
        
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout = this.buildUI();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(contentLayout);
        centeringLayout.setComponentAlignment(contentLayout,
                Alignment.MIDDLE_CENTER);
        this.addComponent(centeringLayout);

    }

    private VerticalLayout buildUI(){
    	VerticalLayout contentLayout = new VerticalLayout();
    	       
		//Initialisierung der UI-Elemente
		this.gridWorkouts = new Grid<>();
		this.gridWorkouts.setCaption("Trainingspläne");
		this.gridWorkouts.setSelectionMode(SelectionMode.SINGLE);
		this.gridWorkouts.setHeightByRows(2);

		this.existingWorkouts = null;
		this.existingExercises = null;

		this.tfAddWorkoutPlanName = new TextField("Neuer Trainingsplan Name");
		this.tfAddWorkoutPlanDesc = new TextField("Neuer Trainingsplan Beschreibung");
		this.btnAddWorkoutPlan = new Button("Trainingsplan hinzufügen");
		this.btnRmvWorkoutPlan = new Button("Trainingsplan löschen");

		this.gridExercises = new Grid<>();
		this.gridExercises.setCaption("Übungen");
		this.gridExercises.setSelectionMode(SelectionMode.SINGLE);
		this.gridExercises.setHeightByRows(2);

		this.tfAddExerciseName = new TextField("Neue Übung Name");
		this.tfAddExerciseTargReps = new TextField("Neue Übung Reps");
		this.tfAddExerciseTargSets = new TextField("Neue Übung Sets");
		this.tfAddExerciseTargWeights = new TextField("Neue Übung Gewicht");
		this.btnAddExercise = new Button("Übung hinzufügen");
		this.btnRmvExercise = new Button("Ausgewählte Übung löschen");
		
		this.workoutPlanCreater = new WorkoutPlanTableConnector();
		this.newWorkout = null;
		
		// Action-Listener definieren
		// Auswahl der Pläne wird geändert
		ExercisesTableConnector exerciseProvider = new ExercisesTableConnector();
		this.gridWorkouts.addSelectionListener(e -> {
			// Name des ausgewählten Plans extrahieren
			String selectedPlanName = null;

			try {
				selectedPlanName = e.getFirstSelectedItem().get().getName();
			} catch (NoSuchElementException ex) {
			}

			this.gridExercises.removeAllColumns();
			if (selectedPlanName != null) {

				// Exercise-Grid laden
				this.existingExercises = exerciseProvider.getExercicesForUsersWorkoutPlan(
						this.getParentUI().getLoggedInUsername(), selectedPlanName);

				// Exercise-Grid-Layout intialisieren
				this.gridExercises.setItems(this.existingExercises);
				this.gridExercises.addColumn(Exercise::getName).setCaption("Name");
				this.gridExercises.addColumn(Exercise::getTargetReps).setCaption("Wiederholungen Ziel");
				this.gridExercises.addColumn(Exercise::getTargetWeight).setCaption("Gewicht Ziel");
				this.gridExercises.addColumn(Exercise::getTargetSets).setCaption("Sätze Ziel");
				if (this.existingExercises.size() != 0) {
					this.gridExercises.setHeightByRows(this.existingExercises.size());
				}
			}
		});

		// Neuen Plan hinzufügen
		this.btnAddWorkoutPlan.addClickListener(e -> {
			//Validitätscheck der eingegebenen Daten
			if (!this.tfAddWorkoutPlanName.getValue().equals("")
					&& !this.tfAddWorkoutPlanDesc.getValue().equals("")) {
				newWorkout = new WorkoutPlan(tfAddWorkoutPlanName.getValue(),
						tfAddWorkoutPlanDesc.getValue(), this.getParentUI().getLoggedInUsername());
				
				//Check, ob eingegebener Plan ggf. schon besteht
				boolean planAlreadyExists = false;
				if(this.existingWorkouts!=null&&this.existingWorkouts.size()!=0){
					for(WorkoutPlan exWork : this.existingWorkouts){
						if(exWork.getName().equals(newWorkout.getName())){
							planAlreadyExists = true;
						}
					}
				}
				
				//Plan wird nur hinzugefügt, wenn Plan nicht bereits besteht
				if(!planAlreadyExists){
					// Plan datenbankseitig hinzufügen
					workoutPlanCreater.addWorkoutPlanForUsername(newWorkout);
	
					// Plan in UI einfügen
					this.existingWorkouts.add(newWorkout);
					this.gridWorkouts.removeAllColumns();
					this.gridWorkouts.setItems(this.existingWorkouts);
					this.gridWorkouts.addColumn(WorkoutPlan::getName).setCaption("Name");
					this.gridWorkouts.addColumn(WorkoutPlan::getDescription).setCaption("Beschreibung");
					if (this.existingWorkouts.size() != 0) {
						this.gridWorkouts.setHeightByRows(this.existingWorkouts.size());
					}
				} else {
					Notification.show("Plan mit diesem Namen besteht schon", "", Notification.Type.HUMANIZED_MESSAGE);
				}
				
			} else {
				Notification.show("Eingaben nicht ausreichend", "", Notification.Type.HUMANIZED_MESSAGE);
			}
		});

		// Plan löschen
		this.btnRmvWorkoutPlan.addClickListener(e -> {
			// Ausgewählten Plan extrahieren
			WorkoutPlan selectedPlan = null;
			Set<WorkoutPlan> selectedPlans = this.gridWorkouts.getSelectedItems();
			for (WorkoutPlan w : selectedPlans) {
				selectedPlan = w;
			}

			if (selectedPlan != null) {
				// Plan datenbankseitig löschen
				WorkoutPlanTableConnector workoutDeleter = new WorkoutPlanTableConnector();
				workoutDeleter.deleteWorkoutPlanForUserName(selectedPlan.getFkUsername(), selectedPlan.getName());
				
				ExercisesTableConnector exerciseDeleter = new ExercisesTableConnector();
				exerciseDeleter.deleteExercisesForUsernameAndPlan(selectedPlan.getFkUsername(), selectedPlan.getName());
				
				LogTableConnector logDeleter = new LogTableConnector();
				logDeleter.deleteLogsForUsernameAndWorkOutPlan(selectedPlan.getFkUsername(), selectedPlan.getName());
				
				// Plan in UI löschen
				this.existingWorkouts.remove(selectedPlan);
				this.gridWorkouts.removeAllColumns();
				this.gridWorkouts.setItems(this.existingWorkouts);
				this.gridWorkouts.addColumn(WorkoutPlan::getName).setCaption("Name");
				this.gridWorkouts.addColumn(WorkoutPlan::getDescription).setCaption("Beschreibung");
				if (this.existingWorkouts.size() != 0) {
					this.gridWorkouts.setHeightByRows(this.existingWorkouts.size());
				}
			} else {
				Notification.show("Kein Plan ausgewählt", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		});

		// Übung hinzufügen
		this.btnAddExercise.addClickListener(e -> {
			//Validitätscheck der eingegebenen Daten
			try{
				if (!this.tfAddExerciseName.getValue().equals("") && !this.tfAddExerciseTargReps.getValue().equals("")
						&& !this.tfAddExerciseTargSets.getValue().equals("")
						&& !this.tfAddExerciseTargWeights.getValue().equals("")) {
					WorkoutPlan selectedPlan = null;
					Set<WorkoutPlan> selectedPlans = this.gridWorkouts.getSelectedItems();
					for (WorkoutPlan w : selectedPlans) {
						selectedPlan = w;
					}
	
					if (selectedPlan != null) {
						Exercise newExercise = new Exercise(tfAddExerciseName.getValue(),
								Integer.parseInt(tfAddExerciseTargReps.getValue()),
								Integer.parseInt(tfAddExerciseTargWeights.getValue()),
								Integer.parseInt(tfAddExerciseTargSets.getValue()), selectedPlan.getFkUsername(),
								selectedPlan.getName());
						
						//Check, ob es die Übung in dem Plan schon gibt
						boolean exerciseAlreadyExists = false;
						if(this.existingExercises!=null&&this.existingExercises.size()!=0){
							for(Exercise exExer : this.existingExercises){
								if(exExer.getName().equals(newExercise.getName())&&exExer.getFkWorkoutplanName().equals(newExercise.getFkWorkoutplanName())){
									exerciseAlreadyExists = true;
								}
							}
						}
						
						//Übung wird nur hinzugefügt, wenn es sie im Plan noch nicht gibt
						if(!exerciseAlreadyExists){
							// Übungen datenbankseitig hinzufügen
							ExercisesTableConnector exerciseAdder = new ExercisesTableConnector();
							exerciseAdder.addExerciseToPlan(newExercise);
		
							// Übungen lokal hinzufügen
							this.gridExercises.removeAllColumns();
							this.existingExercises.add(newExercise);
							this.gridExercises.setItems(this.existingExercises);
							this.gridExercises.addColumn(Exercise::getName).setCaption("Name");
							this.gridExercises.addColumn(Exercise::getTargetReps).setCaption("Wiederholungen Ziel");
							this.gridExercises.addColumn(Exercise::getTargetWeight).setCaption("Gewicht Ziel");
							this.gridExercises.addColumn(Exercise::getTargetSets).setCaption("Sätze Ziel");
							if (this.existingExercises.size() != 0) {
								this.gridExercises.setHeightByRows(this.existingExercises.size());
							}
						}else{
							Notification.show("Übung bereits in Plan enthalten", "", Notification.Type.HUMANIZED_MESSAGE);
						}
						
					} else {
						Notification.show("Kein Plan ausgewählt", "", Notification.Type.HUMANIZED_MESSAGE);
					}
				} else {
					Notification.show("Eingaben nicht ausreichend", "", Notification.Type.HUMANIZED_MESSAGE);
				}
			} catch(NumberFormatException nfe){
				Notification.show("Ein eingegebener Wert war keine Zahl!", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		});

		// Übung löschen
		this.btnRmvExercise.addClickListener(e -> {
			// Ausgewählte Übung extrahieren
			Exercise selectedExercise = null;
			Set<Exercise> selectedExercises = this.gridExercises.getSelectedItems();
			for (Exercise w : selectedExercises) {
				selectedExercise = w;
			}

			if (selectedExercise != null) {
				// Übung datenbankseitig löschen
				ExercisesTableConnector exerciseDeleter = new ExercisesTableConnector();
				exerciseDeleter.deleteExerciseForUsernameAndPlan(selectedExercise.getFkUsername(),
						selectedExercise.getFkWorkoutplanName(), selectedExercise.getName());
				
				LogTableConnector logDeleter = new LogTableConnector();
				logDeleter.deleteLogsForUsernameAndWorkoutPlanAndExercise(selectedExercise.getFkUsername(), selectedExercise.getFkWorkoutplanName(), selectedExercise.getName());
				
				// Übung aus UI löschen
				this.existingExercises.remove(selectedExercise);
				this.gridExercises.removeAllColumns();
				this.gridExercises.setItems(this.existingExercises);
				this.gridExercises.addColumn(Exercise::getName).setCaption("Name");
				this.gridExercises.addColumn(Exercise::getTargetReps).setCaption("Wiederholungen Ziel");
				this.gridExercises.addColumn(Exercise::getTargetWeight).setCaption("Gewicht Ziel");
				this.gridExercises.addColumn(Exercise::getTargetSets).setCaption("Sätze Ziel");
				if (this.existingExercises.size() != 0) {
					this.gridExercises.setHeightByRows(this.existingExercises.size());
				}
			} else {
				Notification.show("Kein Plan ausgewählt", "", Notification.Type.HUMANIZED_MESSAGE);
			}
		});

		// Komponenten final zu Layout hinzufügen
		contentLayout.addComponent(gridWorkouts);
		contentLayout.addComponent(tfAddWorkoutPlanName);
		contentLayout.addComponent(tfAddWorkoutPlanDesc);
		contentLayout.addComponent(btnAddWorkoutPlan);
		contentLayout.addComponent(btnRmvWorkoutPlan);
		contentLayout.addComponent(gridExercises);
		contentLayout.addComponent(tfAddExerciseName);
		contentLayout.addComponent(tfAddExerciseTargReps);
		contentLayout.addComponent(tfAddExerciseTargSets);
		contentLayout.addComponent(tfAddExerciseTargWeights);
		contentLayout.addComponent(btnAddExercise);
		contentLayout.addComponent(btnRmvExercise);
    	
    	return contentLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
		// Workout-Grid laden
		this.existingWorkouts = new ArrayList<>();
		WorkoutPlanTableConnector workoutProvider = new WorkoutPlanTableConnector();
		this.existingWorkouts = workoutProvider
				.getWorkoutplansForUsername(this.getParentUI().getLoggedInUsername());

		// Workout-Grid-Layout intialisieren
		this.gridWorkouts.removeAllColumns();
		this.gridExercises.removeAllColumns();
		this.gridWorkouts.setItems(this.existingWorkouts);
		this.gridWorkouts.addColumn(WorkoutPlan::getName).setCaption("Name");
		this.gridWorkouts.addColumn(WorkoutPlan::getDescription).setCaption("Beschreibung");
		if (this.existingWorkouts.size() != 0) {
			this.gridWorkouts.setHeightByRows(this.existingWorkouts.size());
		}
	
    }
    
    private MyUI getParentUI(){
    	return this.parentUI;
    }

}

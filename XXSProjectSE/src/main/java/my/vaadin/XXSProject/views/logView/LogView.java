package my.vaadin.XXSProject.views.logView;

import java.util.ArrayList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeSelect;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.WorkoutPlan;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class LogView extends CssLayout implements View {

    public static final String VIEW_NAME = "Logs";
	private MyUI parentUi;
	
	private NativeSelect<WorkoutPlan> selectPlan;
	private LogShowDisplay logShowDisplay;
//	private LogShowChart logShowChart;
	private ArrayList<LogAddDisplay> currentLogAddDisplays;

    public LogView() {
        setSizeFull();
        addStyleName("crud-view");
        buildUI();

    }

    private void buildUI() {
        addStyleName("log-view");
        
		
		logShowDisplay = new LogShowDisplay();
		this.addComponent(logShowDisplay);

//		this.logShowChart = new LogShowChart();
//		this.addComponent(logShowChart);
		
		selectPlan = new NativeSelect<>("Logs hinzufügen");
		selectPlan.setEmptySelectionAllowed(false);
		this.addComponent(this.selectPlan);
		
		//Array, in dem alle aktuellen LogAddDisplays gespeichert werden
		this.currentLogAddDisplays = new ArrayList<>();
		
		//Action-Listener - Workoutplan wird ausgewählt
		this.selectPlan.addSelectionListener(e->{
//			this.selectedWorkoutPlanChanged();
		});
        
    }
    
    
    
	//LogShowDisplay aktualisieren
	public void updateLogShow(Exercise exerciseToShow){
		this.logShowDisplay.showLogsForExersice(exerciseToShow);
//		this.logShowChart.showLogsForExercise(exerciseToShow);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
//	//WorkoutPlan wird ausgewählt
//	private void selectedWorkoutPlanChanged(){		
//		WorkoutPlan selectedPlan = this.selectPlan.getSelectedItem().get();
//		
//		ExercisesTableConnector exerciseProvider = new ExercisesTableConnector();
//		List<Exercise> existingExercisesForPlan = exerciseProvider.getExercicesForUsersWorkoutPlan(this.getParentUI().getLoggedInUsername(), selectedPlan.getName());
//		
//		//Check, ob es Übungen für ausgewählten Plan gibt
//		if(existingExercisesForPlan.size()!=0){
//			//Alte Logs entfernen
//			for(LogAddDisplay ld : currentLogAddDisplays){
//				this.removeComponent(ld);
//			}
//			this.currentLogAddDisplays.clear();
//			
//			//Neuen LogView hinzufügen
//			for(Exercise ex : existingExercisesForPlan){
//				this.currentLogAddDisplays.add(new LogAddDisplay(this, ex));
//			}
//			for(LogAddDisplay ld : currentLogAddDisplays){
//				this.addComponent(ld);
//			}
//			
//		} else {
//			//Alte Logs entfernen
//			for(LogAddDisplay ld : currentLogAddDisplays){
//				this.removeComponent(ld);
//			}
//			this.currentLogAddDisplays.clear();
//			
//			Notification.show("Keine Übungen für Plan","", Notification.Type.HUMANIZED_MESSAGE);
//		}
//	
//	}

//	@Override
//	public void enter(ViewChangeEvent event) {
//		//Automatische Weiterleitung zu LoginView, wenn Nutzer nicht angemeldet ist
//		if(this.getParentUI().getSession().getAttribute("user")==null){
//			this.getParentUI().getNavigator().navigateTo(LoginView.VIEW_ID);
//		} else {
//			
//			//Daten für Plan-Select laden		
//			WorkoutPlanTableConnector workoutPlanProvider = new WorkoutPlanTableConnector();
//			List<WorkoutPlan> existingWorkouts = workoutPlanProvider.getWorkoutplansForUsername(this.getParentUI().getLoggedInUsername());
//		
//			if(existingWorkouts.size()!=0){
//				this.selectPlan.setItems(existingWorkouts);
//			}
//			
//		}
//	}


}

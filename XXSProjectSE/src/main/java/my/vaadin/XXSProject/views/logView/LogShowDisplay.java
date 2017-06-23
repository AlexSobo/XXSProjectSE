package my.vaadin.XXSProject.views.logView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.databaseClasses.LogTableConnector;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.Log;

public class LogShowDisplay extends VerticalLayout {
	private Exercise currentExercise;

	private Label lblHeaderText;
	private List<Log> logsToShow;
	private Grid<Log> gridLogs;
	private LogTableConnector logProvider;

	public LogShowDisplay() {

		this.setSizeFull();
		this.setWidth(100, Unit.PERCENTAGE);
		lblHeaderText = new Label();
		logsToShow = new ArrayList<>();
		gridLogs = new Grid();
		gridLogs.setWidth(100, Unit.PERCENTAGE);
		this.gridLogs.setHeightByRows(5);
		logProvider = new LogTableConnector();
	}

	public void showLogsForExersice(Exercise exerciseToShowLogsFor) {
		this.currentExercise = exerciseToShowLogsFor;

		// Bestehende Daten clearen
		if (!this.logsToShow.isEmpty()) {
			this.logsToShow.clear();
			this.gridLogs.removeAllColumns();
		} else {
			this.addComponent(lblHeaderText);
			this.addComponent(gridLogs);
		}

		// Neue Logs anzeigen
		this.logsToShow = logProvider.getLogsForUsernameWorkoutPlanExercise(this.currentExercise.getFkUsername(),
				this.currentExercise.getFkWorkoutplanName(), this.currentExercise.getName());
		Collections.sort(this.logsToShow, new Comparator<Log>() {
			@Override
			public int compare(Log log1, Log log2) {
				if(log1.getDate().before(log2.getDate())){
					return -1;
				} else {
					return 1;
				}
				
			}
         });
		
		
		this.lblHeaderText.setValue("Logs für Übung " + this.currentExercise.getName());
		if (logsToShow.size() != 0) {
			this.gridLogs.setItems(logsToShow);

			this.gridLogs.addColumn(Log::getDate).setCaption("Datum");
			this.gridLogs.addColumn(Log::getReps).setCaption("Wiederholungen");
			this.gridLogs.addColumn(Log::getWeight).setCaption("Gewicht");
			this.gridLogs.addColumn(Log::getSets).setCaption("Sätze");
		} else {
			Notification.show("Keine Logs anzuzeigen", "", Notification.Type.HUMANIZED_MESSAGE);
		}

	}
}

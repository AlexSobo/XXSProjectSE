package my.vaadin.XXSProject.views.logView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.databaseClasses.LogTableConnector;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.Log;
import my.vaadin.XXSProject.webService.XXSWebServerConnector;

public class LogAddDisplay extends VerticalLayout {
	private LogView parentView;

	private List<Log> existingLogs;

	private Exercise exercise;

	private HorizontalLayout repsLayout;
	private HorizontalLayout setsLayout;
	private HorizontalLayout weightsLayout;
	private HorizontalLayout dateLayout;

	private Label lblExerciseName;
	private Button btnShowLogForExercise;
	private TextField tfReps;
	private TextField tfSets;
	private TextField tfWeights;
	private DateField dfDate;

	private Button btnAcceptReps;
	private Button btnAcceptSets;
	private Button btnAcceptWeights;
	private Button btnAcceptDate;
	private Button btnDeclineReps;
	private Button btnDeclineSets;
	private Button btnDeclineWeights;
	private Button btnDeclineDate;

	public LogAddDisplay(LogView parentView, Exercise exercise) {
		super();
		this.parentView = parentView;
		this.exercise = exercise;
		
		// Heutiges Datum wird per Webservice im SQL-Format für Vergleichszwecke extrahiert und in LocalDate umgewandelt
		String sqlDateTodayAsString = XXSWebServerConnector.getSQLDateAsStringFromService();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = formatter.withLocale(Locale.GERMANY);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
		LocalDate localDateToday = LocalDate.parse(sqlDateTodayAsString, formatter);
		
		// UI-Elemente initialisieren
		this.repsLayout = new HorizontalLayout();
		this.setsLayout = new HorizontalLayout();
		this.weightsLayout = new HorizontalLayout();
		this.dateLayout = new HorizontalLayout();

		this.lblExerciseName = new Label(this.exercise.getName());

		this.btnShowLogForExercise = new Button("Logs für Übung anzeigen");

		this.tfReps = new TextField("Wiederholungen");
		this.tfReps.setValue(String.valueOf(this.exercise.getTargetReps()));
		this.tfSets = new TextField("Sätze");
		this.tfSets.setValue(String.valueOf(this.exercise.getTargetSets()));
		this.tfWeights = new TextField("Gewicht");
		this.tfWeights.setValue(String.valueOf(this.exercise.getTargetWeight()));
		this.dfDate = new DateField("Datum");
		this.dfDate.setValue(localDateToday);

		this.btnAcceptReps = new Button("✅");
		this.btnAcceptSets = new Button("✅");
		this.btnAcceptWeights = new Button("✅");
		this.btnAcceptDate = new Button("✅");
		this.btnDeclineReps = new Button("⛔️");
		this.btnDeclineSets = new Button("⛔️");
		this.btnDeclineWeights = new Button("⛔️");
		this.btnDeclineDate = new Button("⛔️");

		this.existingLogs = new ArrayList<>();

		//Bereits existierende Logs laden
		LogTableConnector logProvider = new LogTableConnector();
		this.existingLogs = logProvider.getLogsForUsernameWorkoutPlanExercise(this.exercise.getFkUsername(),
				this.exercise.getFkWorkoutplanName(), this.exercise.getName());

		// ActionListener hinzufügen
		this.btnShowLogForExercise.addClickListener(e -> {
			this.parentView.updateLogShow(this.exercise);
		});

		this.btnAcceptReps.addClickListener(e -> {
			this.repsAccepted();
		});

		this.btnDeclineReps.addClickListener(e -> {
			this.repsDeclined();
		});

		this.btnAcceptSets.addClickListener(e -> {
			this.setsAccepted();
		});

		this.btnDeclineSets.addClickListener(e -> {
			this.setsDeclined();
		});

		this.btnAcceptWeights.addClickListener(e -> {
			this.weightsAccepted();
		});

		this.btnDeclineWeights.addClickListener(e -> {
			this.weightsDeclined();
		});
		
		this.btnAcceptDate.addClickListener(e -> {
			this.dateAccepted();
		});

		this.btnDeclineDate.addClickListener(e -> {
			this.dateDeclined();
		});

		this.tfReps.addFocusListener(e -> {
			this.tfReps.setValue("");
		});

		this.tfWeights.addFocusListener(e -> {
			this.tfWeights.setValue("");
		});

		this.tfSets.addFocusListener(e -> {
			this.tfSets.setValue("");
		});

		this.tfReps.addBlurListener(e -> {
			if (this.tfReps.getValue().equals("")) {
				this.tfReps.setValue(String.valueOf(this.exercise.getTargetReps()));
			}
		});

		this.tfSets.addBlurListener(e -> {
			if (this.tfSets.getValue().equals("")) {
				this.tfSets.setValue(String.valueOf(this.exercise.getTargetSets()));
			}

		});

		this.tfWeights.addBlurListener(e -> {
			if (this.tfWeights.getValue().equals("")) {
				this.tfWeights.setValue(String.valueOf(this.exercise.getTargetWeight()));
			}
		});

		// Elemente hinzufügen
		this.addComponent(lblExerciseName);
		this.addComponent(btnShowLogForExercise);
		this.repsLayout.addComponent(tfReps);
		this.repsLayout.addComponent(btnAcceptReps);
		this.repsLayout.addComponent(btnDeclineReps);
		this.setsLayout.addComponent(tfSets);
		this.setsLayout.addComponent(btnAcceptSets);
		this.setsLayout.addComponent(btnDeclineSets);
		this.weightsLayout.addComponent(tfWeights);
		this.weightsLayout.addComponent(btnAcceptWeights);
		this.weightsLayout.addComponent(btnDeclineWeights);
		this.dateLayout.addComponent(dfDate);
		this.dateLayout.addComponent(btnAcceptDate);
		this.dateLayout.addComponent(btnDeclineDate);
		this.addComponent(repsLayout);
		this.addComponent(setsLayout);
		this.addComponent(weightsLayout);
		this.addComponent(dateLayout);
	}

	// Button-Actions
	private void repsAccepted() {
		if (!this.tfReps.getValue().equals("")) {
			try {
				int enteredValue = Integer.parseInt(tfReps.getValue());
				this.tfReps.setEnabled(false);
				this.btnAcceptReps.setEnabled(false);
				this.btnDeclineReps.setEnabled(false);

				if (this.allAcceptButtonsClicked()) {
					this.invokeLogWriteToDatabase();
				}
			} catch (NumberFormatException e) {
				this.repsDeclined();
				Notification.show("Eingegebener Wert ungültig", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		} else {
			Notification.show("Falscheingabe", "", Notification.Type.HUMANIZED_MESSAGE);
		}
	}

	private void repsDeclined() {
		this.tfReps.setValue("");
		this.tfReps.focus();
	}

	private void setsAccepted() {
		if (!this.tfSets.getValue().equals("")) {
			try {
				int enteredValue = Integer.parseInt(tfSets.getValue());
				this.tfSets.setEnabled(false);
				this.btnAcceptSets.setEnabled(false);
				this.btnDeclineSets.setEnabled(false);

				if (this.allAcceptButtonsClicked()) {
					this.invokeLogWriteToDatabase();
				}
			} catch (NumberFormatException e) {
				this.setsDeclined();
				Notification.show("Eingegebener Wert ungültig", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		} else {
			Notification.show("Falscheingabe", "", Notification.Type.HUMANIZED_MESSAGE);
		}
	}

	private void setsDeclined() {
		this.tfSets.setValue("");
		this.tfSets.focus();
	}

	private void weightsAccepted() {
		if (!this.tfWeights.getValue().equals("")) {
			try {
				int enteredValue = Integer.parseInt(tfWeights.getValue());
				this.tfWeights.setEnabled(false);
				this.btnAcceptWeights.setEnabled(false);
				this.btnDeclineWeights.setEnabled(false);

				if (this.allAcceptButtonsClicked()) {
					this.invokeLogWriteToDatabase();
				}
			} catch (NumberFormatException e) {
				this.weightsDeclined();
				Notification.show("Eingegebener Wert ungültig", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		} else {
			Notification.show("Falscheingabe", "", Notification.Type.HUMANIZED_MESSAGE);
		}
	}

	private void weightsDeclined() {
		this.tfWeights.setValue("");
		this.tfWeights.focus();
	}
	
	private void dateAccepted(){
		if (!this.dfDate.getValue().equals("")) {
			try {
				this.dfDate.setEnabled(false);
				this.btnAcceptDate.setEnabled(false);
				this.btnDeclineDate.setEnabled(false);

				if (this.allAcceptButtonsClicked()) {
					this.invokeLogWriteToDatabase();
				}
			} catch (Exception e) {
				this.dateDeclined();
				Notification.show("Eingegebener Wert ungültig", "", Notification.Type.HUMANIZED_MESSAGE);
			}

		} else {
			Notification.show("Falscheingabe", "", Notification.Type.HUMANIZED_MESSAGE);
		}
	}
	
	private void dateDeclined(){
		this.dfDate.clear();
		this.dfDate.focus();
	}

	// Andere Methoden
	private boolean allAcceptButtonsClicked() {
		if (!this.btnAcceptReps.isEnabled() && !this.btnAcceptSets.isEnabled() && !this.btnAcceptWeights.isEnabled() && !this.btnAcceptDate.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	private void invokeLogWriteToDatabase() {
		Date javaDate = Date.from(this.dfDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlInputDate = new java.sql.Date(javaDate.getTime());
		
		//Check, ob schon Log mit Datum vorhanden
		boolean logAlreadyExisting = false;
		for (Log exLog : existingLogs) {
			if (this.exercise.getName().equals(exLog.getFkExercisename())
					&& this.exercise.getFkUsername().equals(exLog.getFkUsername())
					&& exLog.getDate().toString().equals(sqlInputDate.toString())
					&& this.exercise.getFkWorkoutplanName().equals(exLog.getFkWorkoutplan())) {
				logAlreadyExisting = true;
			}
		}
		
		if(!logAlreadyExisting){
			Log logTooAdd = new Log(sqlInputDate, Integer.parseInt(this.tfReps.getValue()),
					Integer.parseInt(this.tfWeights.getValue()), Integer.parseInt(this.tfSets.getValue()),
					this.exercise.getFkUsername(), this.exercise.getFkWorkoutplanName(), this.exercise.getName());

			LogTableConnector logWriter = new LogTableConnector();
			logWriter.addLog(logTooAdd);

			Notification.show("Log gespeichert", "", Notification.Type.HUMANIZED_MESSAGE);
			this.parentView.updateLogShow(this.exercise);
			
			//Bereits existierende Logs neu laden
			LogTableConnector logProvider = new LogTableConnector();
			this.existingLogs = logProvider.getLogsForUsernameWorkoutPlanExercise(this.exercise.getFkUsername(),
					this.exercise.getFkWorkoutplanName(), this.exercise.getName());

			this.lblExerciseName.setValue(this.lblExerciseName.getValue() + " - gespeichert ✅");
		} else {
			Notification.show("Es exisitert bereits ein Log für diese Übung und diesen Tag", "", Notification.Type.HUMANIZED_MESSAGE);
			this.btnAcceptDate.setEnabled(true);
			this.btnDeclineDate.setEnabled(true);
			this.dfDate.setEnabled(true);
			this.dfDate.focus();
		}
		
	}
}

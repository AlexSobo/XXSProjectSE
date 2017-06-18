package my.vaadin.XXSProject.views.settingsView;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseClasses.UserTableConnector;
import my.vaadin.XXSProject.databaseEntities.User;

public class SettingsView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Settings";

	private MyUI parentUI;
	private TextField tfFirstName, tfLastName, tfEmail;
	private PasswordField tfPasswordOld, tfPasswordNew, tfPasswordRepeat;
	private Button btnChange, btnSave;
	private Label lblError;
	private List<User> user;

	public SettingsView(MyUI ui) {
		this.parentUI = ui;

		setSizeFull();
		setMargin(false);
		setStyleName("Settings-View");

		VerticalLayout centeringLayout = new VerticalLayout();
		centeringLayout.setMargin(false);
		centeringLayout.setSpacing(false);
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout = this.buildUI();
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(contentLayout);
		centeringLayout.setComponentAlignment(contentLayout, Alignment.MIDDLE_CENTER);
		this.addComponent(centeringLayout);
	}

	private VerticalLayout buildUI() {
		VerticalLayout contentLayout = new VerticalLayout();

		this.tfPasswordOld = new PasswordField("Altes Passwort:");
		this.tfPasswordNew = new PasswordField("Neues Passwort:");
		this.tfPasswordRepeat = new PasswordField("Passwort wiederholen:");
		this.tfEmail = new TextField("E-Mail Adresse:");
		this.tfFirstName = new TextField("Vorname:");
		this.tfLastName = new TextField("Nachname:");
		this.btnChange = new Button("Ändern");
		this.btnSave = new Button("Passwort speichern");

		// Standardlayout am Anfang
		contentLayout.addComponent(tfFirstName);
		contentLayout.addComponent(tfLastName);
		contentLayout.addComponent(tfEmail);
		contentLayout.addComponent(btnChange);
		contentLayout.addComponent(tfPasswordOld);
		contentLayout.addComponent(tfPasswordNew);
		contentLayout.addComponent(tfPasswordRepeat);
		contentLayout.addComponent(btnSave);

		this.user = null;

		this.btnChange.addClickListener(Event -> {

			// Datenbankverbindung herstellen
			UserTableConnector userTableConnector = new UserTableConnector();
			User userNow = userTableConnector.returnUser(this.getParentUI().getLoggedInUsername());
			System.out.println(userNow);

			// Prüfen, ob Eingabefelder überhaupt ausgefüllt sind
			if (!tfFirstName.getValue().equals(userNow.getFirstName())
					|| !tfLastName.getValue().equals(userNow.getLastName())
					|| !tfEmail.getValue().equals(userNow.getEmailAddress())) {

				// User updaten
				userTableConnector.updateUserDetails(tfFirstName, tfLastName, tfEmail,
						this.getParentUI().getLoggedInUsername());
				Notification.show("Ihre Angaben wurden geändert", "", Notification.Type.HUMANIZED_MESSAGE);
			}
		});

		this.btnSave.addClickListener(Event -> {

			// Datenbankverbindung herstellen
			UserTableConnector userTableConnector = new UserTableConnector();
			User userNow = userTableConnector.returnUser(this.getParentUI().getLoggedInUsername());

			// Prüfen, ob altes Passwort korrekt eingegeben worden ist
			if (String.valueOf(tfPasswordOld.getValue().hashCode()).equals(userNow.getPassword())) {

				if (tfPasswordNew.getValue().equals(tfPasswordRepeat.getValue()) && tfPasswordNew.getValue() != "") {
					userTableConnector.changePassword(tfPasswordOld, tfPasswordNew, tfPasswordNew,
							this.getParentUI().getLoggedInUsername());
					this.tfPasswordOld.clear();
					this.tfPasswordNew.clear();
					this.tfPasswordRepeat.clear();
					Notification.show("Ihr neues Passwort wurde gespeichert", "", Notification.Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Überprüfen Sie Ihre Angaben", "", Notification.Type.HUMANIZED_MESSAGE);
				}
			} else {
				Notification.show("Falsches Passwort", "", Notification.Type.HUMANIZED_MESSAGE);
			}
		});

		return contentLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("Enter Methode wird ausgefuehrt");
		// Automatische Weiterleitung zu LoginView, wenn Nutzer nicht angemeldet
		// ist

		// Vorausfüllen der Felder mit Daten aus der Datenbank
		UserTableConnector userTableConnector = new UserTableConnector();
		User userNow = userTableConnector.returnUser(this.getParentUI().getLoggedInUsername());

		this.tfFirstName.setValue(userNow.getFirstName());
		this.tfLastName.setValue(userNow.getLastName());
		this.tfEmail.setValue(userNow.getEmailAddress());
		this.tfPasswordOld.clear();
		this.tfPasswordNew.clear();
		this.tfPasswordRepeat.clear();

	}

	private MyUI getParentUI() {
		return this.parentUI;
	}

}
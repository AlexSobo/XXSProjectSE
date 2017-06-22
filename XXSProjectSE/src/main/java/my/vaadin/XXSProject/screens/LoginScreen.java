package my.vaadin.XXSProject.screens;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import my.vaadin.XXSProject.MyUI;

/**
 * LoginScreen + Überprüfung der Logindaten.
 */
public class LoginScreen extends CssLayout {

	private MyUI parentUi;
	private TextField tfUsername, tfUsernameNew, tfFirstName, tfLastName, tfEmail;
	private PasswordField pfPassword, pfPasswordNew, pfPasswortRepeat;
	private Button btnLogin, btnRegister;
	private Button forgotPassword;
	private CheckBox boxRegistered;
	private VerticalLayout centeringLayout;
	private FormLayout loginForm;
	private FormLayout registerForm;

	public LoginScreen(MyUI ui) {
		this.parentUi = ui;
		parentUi.getLoginService();

		if (this.parentUi.getLoggedInUsername() == null) {
			buildUI();
			tfUsername.focus();
		} else {
			parentUi.get().showMainView();
		}

	}

	private void buildUI() {
		addStyleName("login-screen");

		// Aufbau der zwei Formulare für Login und Registrierung
		loginForm = buildLoginForm();
		registerForm = buildRegisterForm();

		// Erstellen eines Layouts, um den Inhalt zu zentrieren
		centeringLayout = new VerticalLayout();
		centeringLayout.setMargin(false);
		centeringLayout.setSpacing(false);
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(loginForm);
		centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

		// Layout mit Informationen zum Login
		CssLayout loginInformation = buildLoginInformation();

		// Hinzufügen der Layouts zum LoginScreen
		addComponent(centeringLayout);
		addComponent(loginInformation);

	}

	private FormLayout buildLoginForm() {

		FormLayout loginForm = new FormLayout();

		loginForm.addStyleName("login-form");
		loginForm.setSizeUndefined();
		loginForm.setMargin(false);
		loginForm.addComponent(this.tfUsername = new TextField("Benutzername"));
		this.tfUsername.setWidth(15, Unit.EM);
		loginForm.addComponent(this.pfPassword = new PasswordField("Passwort"));
		this.pfPassword.setWidth(15, Unit.EM);
		CssLayout buttons = new CssLayout();
		buttons.setStyleName("buttons");

		buttons.addComponent(btnLogin = new Button("Login"));
		btnLogin.addClickListener(Event -> {
			btnLoginWasClicked();
		});
		btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnLogin.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		buttons.addComponent(forgotPassword = new Button("Passwort vergessen?"));
		forgotPassword.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				showNotification(new Notification("tbd!"));
			}
		});
		forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);

		loginForm.addComponent(buttons);
		loginForm.addComponent(this.boxRegistered = new CheckBox("Bereits registriert"));
		this.boxRegistered.setValue(true);
		this.boxRegistered.addValueChangeListener(event -> {
			this.checkBoxValueChanged();
		});

		return loginForm;
	}

	private FormLayout buildRegisterForm() {

		FormLayout registerForm = new FormLayout();

		registerForm.addStyleName("login-form");
		registerForm.setSizeUndefined();
		registerForm.setMargin(false);
		registerForm.addComponent(this.tfUsernameNew = new TextField("Benutzername"));
		this.tfUsernameNew.setWidth(15, Unit.EM);
		registerForm.addComponent(this.tfEmail = new TextField("E-Mail"));
		this.tfEmail.setWidth(15, Unit.EM);
		registerForm.addComponent(this.tfFirstName = new TextField("Vorname"));
		this.tfFirstName.setWidth(15, Unit.EM);
		registerForm.addComponent(this.tfLastName = new TextField("Nachname"));
		this.tfLastName.setWidth(15, Unit.EM);
		registerForm.addComponent(this.pfPasswordNew = new PasswordField("Passwort"));
		this.pfPasswordNew.setWidth(15, Unit.EM);
		registerForm.addComponent(this.pfPasswortRepeat = new PasswordField("Passwort wiederholen"));
		this.pfPasswortRepeat.setWidth(15, Unit.EM);

		CssLayout buttons = new CssLayout();
		buttons.setStyleName("buttons");
		buttons.addComponent(this.btnRegister = new Button("Registrieren"));
		btnRegister.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnRegister.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnRegister.addClickListener(event -> {
			this.btnRegisterWasClicked();
		});
		registerForm.addComponent(buttons);

		return registerForm;
	}

	private CssLayout buildLoginInformation() {
		CssLayout loginInformation = new CssLayout();
		loginInformation.setStyleName("login-information");
		Label loginInfoText = new Label("<h1>XXS PumperApp</h1>" + "Ein Projekt von Felix, Alex und Dennis" + "<br>Tracking gainz, looking good and feeling great.",
				ContentMode.HTML);
		loginInfoText.setSizeFull();
		loginInformation.addComponent(loginInfoText);
		return loginInformation;
	}

	// Action, wenn Login-Button gedrückt wird
	private void btnLoginWasClicked() {
		if (this.tfUsername.getValue().equals("") || this.pfPassword.getValue().equals("")) {
			showNotification(new Notification("Daten unvollständig eingegeben!"));
		} else {
			// Check, ob Username und Passwort in Datenbank vorhanden
			if (this.parentUi.getLoginService().logIn(this.tfUsername.getValue(), pfPassword.getValue())) {
				parentUi.get().showMainView();
			} else {
				showNotification(new Notification("Benutzername und/oder Passwort nicht bekannt!"));
			}
		}
	}

	// Action, wenn Checkbox Wert ändert
	private void checkBoxValueChanged() {
		if (this.boxRegistered.getValue()) {
			this.centeringLayout.removeAllComponents();
			this.centeringLayout.addComponent(this.loginForm);
			this.loginForm.addComponent(this.boxRegistered);
			centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
		} else {
			this.centeringLayout.removeAllComponents();
			this.centeringLayout.addComponent(this.registerForm);
			this.registerForm.addComponent(this.boxRegistered);
			centeringLayout.setComponentAlignment(registerForm, Alignment.MIDDLE_CENTER);
		}
	}

	// Action, wenn Registrierungs-Button gedrückt wird
	private void btnRegisterWasClicked() {

		// Test, ob Daten vollständig eingegeben
		if (tfUsernameNew.getValue().equals("") || tfEmail.getValue().equals("") || tfFirstName.getValue().equals("")
				|| tfLastName.getValue().equals("") || pfPasswordNew.getValue().equals("")
				|| pfPasswortRepeat.getValue().equals("")) {
			showNotification(new Notification("Daten unvollständig eingegeben!"));
			// Test, ob Passwörter gleich
		} else if (!pfPasswordNew.getValue().equals(pfPasswortRepeat.getValue())) {
			showNotification(new Notification("Passwörter nicht identisch!"));
		} else {
			// Versuch, User zu registrieren
			if (this.parentUi.getRegisterService().registerUser(tfUsernameNew.getValue(), pfPasswordNew.getValue(),
					tfEmail.getValue(), tfFirstName.getValue(), tfLastName.getValue())) {
				parentUi.showMainView();
				// Benutzername oder Mailadresse bereits vorhanden
			} else {
				showNotification(new Notification("Daten bereits vorhanden!"));
			}
		}

	}

	private void showNotification(Notification notification) {
		notification.setDelayMsec(2000);
		notification.show(Page.getCurrent());
	}

}

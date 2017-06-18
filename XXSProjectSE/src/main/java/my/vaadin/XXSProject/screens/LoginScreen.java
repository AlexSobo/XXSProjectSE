package my.vaadin.XXSProject.screens;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseClasses.LoginService;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

	private MyUI parentUi;
    private TextField tfUsername;
    private PasswordField pfPassword;
    private Button btnLogin;
    private Button forgotPassword;
    private CheckBox boxRegistered;
    private LoginListener loginListener;
    private LoginService loginService;

    public LoginScreen(MyUI ui) {
    	this.parentUi = ui;
    	
    	if(this.parentUi.getLoggedInUsername()==null){
    		buildUI();
            tfUsername.focus();
    	} else {
    		parentUi.get().showMainView();
    	}
       
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();
        Component registerForm = buildRegisterForm();
        

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {

		FormLayout loginForm = new FormLayout();

		loginForm.addStyleName("login-form");
		loginForm.setSizeUndefined();
		loginForm.setMargin(false);

		loginForm.addComponent(this.tfUsername = new TextField("Benutzername"));
		this.tfUsername.setWidth(15, Unit.EM);
		loginForm.addComponent(this.pfPassword = new PasswordField("Passwort"));
		this.pfPassword.setWidth(15, Unit.EM);
		this.pfPassword.setDescription("Passwort");
		loginForm.addComponent(this.boxRegistered = new CheckBox("Bereits registriert"));
		this.boxRegistered.setWidth(5, Unit.EM);
		this.boxRegistered.setValue(true);
		CssLayout buttons = new CssLayout();
		buttons.setStyleName("buttons");
		loginForm.addComponent(buttons);
		
		buttons.addComponent(btnLogin = new Button("Dummy-Login"));
		btnLogin.addClickListener(Event -> {
			System.out.println("Bin aktiv");
			btnDummyLoginWasClicked();
		});
		btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnLogin.addStyleName(ValoTheme.BUTTON_FRIENDLY);

		buttons.addComponent(forgotPassword = new Button("Passwort vergessen?"));
		forgotPassword.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				showNotification(new Notification("Haha gibt keine Funktion zum Zurücksetzen!"));
			}
		});
		forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
		this.boxRegistered.setWidth(5, Unit.EM);
		return loginForm;
    }
    
    private Component buildRegisterForm(){
    	this.addComponent(boxRegistered);
    	return null;
    }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(
                "<h1>XXS PumperApp - Ein Projekt von Felix, Alex und Dennis</h1>"
                        + "Für einen uneingeschränkten Zugang, logge dich mit &quot;admin&quot; ein. Mit jedem anderen Benutzernamen erhältst du Zugang mit Leserechten. Für alle Nutzer ist kein Passwort erforderlich!",
                ContentMode.HTML);
        loginInfoText.setSizeFull();
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }
    
	// Action, wenn Dummy-Login Button gedrückt wird
	private void btnDummyLoginWasClicked() {
		System.out.println("dummyMethode");
		parentUi.getLoginService().dummyLogIn();
		parentUi.get().showMainView();
	}


    private void showNotification(Notification notification) {
        // keep the notification visible a little while after moving the
        // mouse, or until clicked
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

}

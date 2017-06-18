package my.vaadin.XXSProject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import my.vaadin.XXSProject.databaseClasses.LoginService;
import my.vaadin.XXSProject.databaseClasses.RegisterService;
import my.vaadin.XXSProject.databaseEntities.User;
import my.vaadin.XXSProject.screens.LoginScreen;
import my.vaadin.XXSProject.screens.MainScreen;

/**
 * Herlich Willkommen im Herzstück der XXS-Pumperapp!
 * 
 * Dies ist die Hauptklasse der Applikation, die das Layout bildet und initial prüft, 
 * ob ein User eingeloggt ist. Ist dies der Fall, startet der Mainscreen der Applikation, 
 * andernfalls öffnet sich Startbildschirm, auf dem man sich einloggen bzw. registrieren kann
 *
 * @Viewport sorgt automatisch für eine richtige Skalierung auf kleinen Geräten.
 * FÜr das Theming wird die Vaadin Integration Valo verwendet.
 **/


@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")

public class MyUI extends UI {
	
	private String loggedInUsername;
	
	private final LoginService loginService = new LoginService(this);
	private final RegisterService registerService = new RegisterService(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	this.loggedInUsername = null;
    	
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("XXS PumperApp");
        setContent(new LoginScreen(this));
//        showMainView();
        
    }

    public void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

	//Getter und Setter
    public MyUI get() {
        return (MyUI) UI.getCurrent();
    }

	public LoginService getLoginService() {
		return loginService;
	}
	
	public RegisterService getRegisterService() {
		return registerService;
	}
	
	public String getLoggedInUsername(){
		return (String)this.getSession().getAttribute("user");
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}

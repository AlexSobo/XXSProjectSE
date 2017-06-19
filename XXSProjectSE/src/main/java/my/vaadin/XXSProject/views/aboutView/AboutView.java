package my.vaadin.XXSProject.views.aboutView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.MyUI;

public class AboutView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Über das Projekt";

	private MyUI parentUI;

	public AboutView(MyUI ui) {
		this.parentUI = ui;
		CustomLayout aboutContent = new CustomLayout("aboutview");
		aboutContent.setStyleName("about-content");

		aboutContent.addComponent(new Label(
				VaadinIcons.INFO_CIRCLE.getHtml() + "<p>HTML-Frontend (Design mit CSS, responsive)</p>"
						+ "<p>Öffentlicher und geschützter Anwendungs-Bereich (Registrierung, Login)</p>"
						+ "<p>Darstellung / Einbindung von Inhalten aus einer Datenbank</p>"
						+ "<p>Bereitstellung eines Webservices</p>"
						+ "<p>Nutzung eines Webservices im Frontend mit AJAX</p>"
						+ "<p>Entwurf / Dokumentation der Anwendung mit Methoden des Software-Engineerings</p>"
						+ "<p>Installationsbeschreibung (Deployment, Konfiguration, ggf. Testdaten)</p>",
				ContentMode.HTML), "info");

		setSizeFull();
		setMargin(false);
		setStyleName("about-view");
		addComponent(aboutContent);
		setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}

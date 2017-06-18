package my.vaadin.XXSProject.screens;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * View, der angezeigt wird, wenn man sich in die App einloggt.
 * 
 */
public class StartView extends VerticalLayout implements View {

	public StartView() {
		Label header = new Label("Herzlich Willkommen in der XXS-Pumperapp!");
		header.addStyleName(ValoTheme.LABEL_H1);
		addComponent(header);
		addComponent(new Label("Viel Spaß! Pump up your Life!"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

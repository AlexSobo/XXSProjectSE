package my.vaadin.XXSProject.views.settingsView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SettingsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Settings";

    public SettingsView() {

        setSizeFull();
        setMargin(false);
        setStyleName("about-view");

    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
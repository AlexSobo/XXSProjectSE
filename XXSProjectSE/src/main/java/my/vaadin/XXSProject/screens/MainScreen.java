package my.vaadin.XXSProject.screens;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.views.aboutView.AboutView;
import my.vaadin.XXSProject.views.logView.LogView;
import my.vaadin.XXSProject.views.overviewView.OverviewView;
import my.vaadin.XXSProject.views.settingsView.SettingsView;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
    private NavigatorMenu navigatorMenu;

    public MainScreen(MyUI ui) {

        setSpacing(false);
        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(StartView.class);
        navigatorMenu = new NavigatorMenu(navigator);
        navigatorMenu.addView(new OverviewView(), OverviewView.VIEW_NAME,
        		OverviewView.VIEW_NAME, VaadinIcons.HOME);
        navigatorMenu.addView(new LogView(), LogView.VIEW_NAME, LogView.VIEW_NAME,
                VaadinIcons.DATE_INPUT);
        navigatorMenu.addView(new SettingsView(), SettingsView.VIEW_NAME, SettingsView.VIEW_NAME,
                VaadinIcons.COGS);
        navigatorMenu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
                VaadinIcons.INFO_CIRCLE);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(navigatorMenu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            navigatorMenu.setActiveView(event.getViewName());
        }

    };
}

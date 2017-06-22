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
 * Innenbereich der App
 */
public class MainScreen extends HorizontalLayout {
	private NavigatorMenu navigatorMenu;
	
	final Navigator navigator;

	public MainScreen(MyUI ui) {

		setSpacing(false);
		setStyleName("main-screen");

		CssLayout viewContainer = new CssLayout();
		viewContainer.addStyleName("valo-content");
		viewContainer.setSizeFull();

		navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(StartView.class);
		navigatorMenu = new NavigatorMenu(navigator);
		navigatorMenu.addView(new OverviewView(ui), OverviewView.VIEW_NAME, OverviewView.VIEW_NAME, VaadinIcons.HOME);
		navigatorMenu.addView(new LogView(ui), LogView.VIEW_NAME, LogView.VIEW_NAME, VaadinIcons.DATE_INPUT);
		navigatorMenu.addView(new SettingsView(ui), SettingsView.VIEW_NAME, SettingsView.VIEW_NAME, VaadinIcons.COGS);
		navigatorMenu.addView(new AboutView(ui), AboutView.VIEW_NAME, AboutView.VIEW_NAME, VaadinIcons.INFO_CIRCLE);

		navigator.addViewChangeListener(viewChangeListener);

		navigator.navigateTo(OverviewView.VIEW_NAME);
		navigatorMenu.setActiveView(OverviewView.VIEW_NAME);

		addComponent(navigatorMenu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
	}

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

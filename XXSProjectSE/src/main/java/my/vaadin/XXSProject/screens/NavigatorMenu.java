package my.vaadin.XXSProject.screens;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

import my.vaadin.XXSProject.views.overviewView.OverviewView;

/**
 * Responsive Navigationsmenü mit den verschiedenen Views.
 */
public class NavigatorMenu extends CssLayout {

	private static final String VALO_MENUITEMS = "valo-menuitems";
	private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
	private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
	private Navigator navigator;
	private Map<String, Button> viewButtons = new HashMap<String, Button>();

	private CssLayout menuItemsLayout;
	private CssLayout menuPart;

	public NavigatorMenu(Navigator navigator) {
		this.navigator = navigator;
		setPrimaryStyleName(ValoTheme.MENU_ROOT);
		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);

		final HorizontalLayout top = new HorizontalLayout();
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		Label title = new Label("XXS Pumperapp");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.setSizeUndefined();
		Image image = new Image(null, new ThemeResource("img/pump-icon.png"));
		image.setStyleName("logo");
		top.addComponent(image);
		top.addComponent(title);
		menuPart.addComponent(top);

		MenuBar logoutMenu = new MenuBar();
		logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				VaadinSession.getCurrent().getSession().invalidate();
				Page.getCurrent().reload();
			}
		});

		logoutMenu.addStyleName("user-menu");
		menuPart.addComponent(logoutMenu);

		// Button zum Menü öffnen bei kleineren Bildschirmen
		final Button showMenu = new Button("Menu", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
					menuPart.removeStyleName(VALO_MENU_VISIBLE);
				} else {
					menuPart.addStyleName(VALO_MENU_VISIBLE);
				}
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName(VALO_MENU_TOGGLE);
		showMenu.setIcon(VaadinIcons.MENU);
		menuPart.addComponent(showMenu);

		// container for the navigation buttons, which are added by addView()
		menuItemsLayout = new CssLayout();
		menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
		menuPart.addComponent(menuItemsLayout);

		addComponent(menuPart);
	}

	public void addView(View view, final String name, String caption, Resource icon) {
		navigator.addView(name, view);
		createViewButton(name, caption, icon);
	}

	public void addView(Class<? extends View> viewClass, final String name, String caption, Resource icon) {
		navigator.addView(name, viewClass);
		createViewButton(name, caption, icon);
	}

	private void createViewButton(final String name, String caption, Resource icon) {
		Button button = new Button(caption, new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(name);

			}
		});
		button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		button.setIcon(icon);
		menuItemsLayout.addComponent(button);
		viewButtons.put(name, button);
	}

	public void setActiveView(String viewName) {
		for (Button button : viewButtons.values()) {
			button.removeStyleName("selected");
		}
		Button selected = viewButtons.get(viewName);
		if (selected != null) {
			selected.addStyleName("selected");
		}
		menuPart.removeStyleName(VALO_MENU_VISIBLE);
	}
}

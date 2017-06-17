package my.vaadin.XXSProject.views.overviewView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class OverviewView extends CssLayout implements View {

    public static final String VIEW_NAME = "Übersicht";

    public OverviewView() {
        setSizeFull();
        addStyleName("crud-view");

    }



    @Override
    public void enter(ViewChangeEvent event) {
       
    }


}

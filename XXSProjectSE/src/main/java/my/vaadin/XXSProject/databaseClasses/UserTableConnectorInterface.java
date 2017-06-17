package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import my.vaadin.XXSProject.databaseEntities.User;

public interface UserTableConnectorInterface {

	void updateUserDetails(TextField firstName, TextField lastName, TextField email, String userName);

	void changePassword(PasswordField oldPassword, PasswordField newPassword, PasswordField repeatPassword,
			String userName);

	User returnUser(String userName);
}

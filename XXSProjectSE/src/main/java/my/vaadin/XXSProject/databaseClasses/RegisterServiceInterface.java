package my.vaadin.XXSProject.databaseClasses;

public interface RegisterServiceInterface {
	public boolean registerUser(String userName, String password, String emailAddress, String firstName,
			String lastName);
}

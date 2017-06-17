package my.vaadin.XXSProject.databaseClasses;

public interface LoginServiceInterface {
	public boolean logIn(String userName, String password);

	public void logOut();
}

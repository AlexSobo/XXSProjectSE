package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseEntities.User;

public class LoginService implements LoginServiceInterface {

	private MyUI parentUI;
	private User loggedInUser;

	public LoginService(MyUI parentUI) {
		super();
		this.parentUI = parentUI;
		this.loggedInUser = null;
	}

	// Getter-Methoden
	public MyUI get() {
		return parentUI;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	// Methoden
	@Override
	public boolean logIn(String inputUserName, String inputPassword) {
		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		List<User> databaseUsers = em.createQuery("SELECT u FROM users u").getResultList();
		System.out.println("im Login");

		// Abgleich eingegebener Daten mit Datenbank-Bestand
		for (User u : databaseUsers) {
			if (u.getUsername().equals(inputUserName)
					&& u.getPassword().equals(String.valueOf(inputPassword.hashCode()))) {
				this.loggedInUser = u;
				this.get().getSession().setAttribute("user", inputUserName);
				em.clear();
				em.close();
				emf.close();
				return true;
			}
		}
		return false;

	}

	@Override
	public void logOut() {
		this.get().getSession().setAttribute("user", null);

	}

	// Über diese Funktion können lokal Dummy-Testdaten in die Session geladen
	// werden
	public boolean dummyLogIn() {
		parentUI.get().getSession().setAttribute("user", "dennsi");
		return true;
	}
}

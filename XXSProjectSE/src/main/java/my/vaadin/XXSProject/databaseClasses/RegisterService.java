package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import my.vaadin.XXSProject.MyUI;
import my.vaadin.XXSProject.databaseEntities.User;

public class RegisterService implements RegisterServiceInterface {

	private MyUI parentUI;

	public RegisterService(MyUI parentUI) {
		super();
		this.parentUI = parentUI;
	}

	@Override
	public boolean registerUser(String inputUserName, String inputPassword, String inputEmailAddress,
			String inputFirstName, String inputLastName) {
		// Schritt 1: Daten aus Datenbank auslesen und Einzigartigkeit des
		// Usernamen und der Mail-Adresse prüfen
		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		List<User> users = em.createQuery("SELECT u FROM users u").getResultList();

		// Abgleich eingegebener Daten mit Datenbank-Bestand
		for (User u : users) {
			if (u.getUsername().equals(inputUserName) || u.getEmailAddress().equals(inputEmailAddress)) {
				return false;
			}
		}

		// Schritt 2: Einschreiben und einloggen des Users
		User newUser = new User(inputUserName, String.valueOf(inputPassword.hashCode()), inputFirstName, inputLastName, inputEmailAddress);
		em.getTransaction().begin();
		em.persist(newUser);
		em.getTransaction().commit();
		em.close();
		emf.close();

		this.parentUI.getSession().setAttribute("user", inputUserName);
//		this.parentUI.updateNavigation();
		return true;
	}

}

package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import my.vaadin.XXSProject.databaseEntities.User;

public class UserTableConnector implements UserTableConnectorInterface {

	@Override
	public void updateUserDetails(TextField firstName, TextField lastName, TextField email, String userName) {

		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		User updatedUser = em.find(User.class, userName);
		if (!firstName.getValue().equals(updatedUser.getFirstName())) {
			updatedUser.setFirstName(firstName.getValue());
			em.persist(updatedUser);
		}
		if (!lastName.getValue().equals(updatedUser.getLastName())) {
			updatedUser.setLastName(lastName.getValue());
			em.persist(updatedUser);
		}
		if (!email.getValue().equals(updatedUser.getEmailAddress())) {
			updatedUser.setEmailAddress(email.getValue());
			em.persist(updatedUser);
		}
		em.flush();
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public User returnUser(String userName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();
		User dummy = em.find(User.class, userName);
		em.clear();
		em.close();
		emf.close();
		return dummy;
	}

	@Override
	public void changePassword(PasswordField oldPassword, PasswordField newPassword, PasswordField repeatPassword,
			String userName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User updatedUser = em.find(User.class, userName);
		System.out.println("Feldwert als Hash: " + oldPassword.getValue());
		System.out.println("UpdatedUser Passwort: " + updatedUser.getPassword());
		if (String.valueOf(oldPassword.getValue().hashCode()).equals(updatedUser.getPassword())
				&& newPassword.getValue().equals(repeatPassword.getValue())) {
			updatedUser.setPassword(String.valueOf(newPassword.getValue().hashCode()));
			em.persist(updatedUser);
		}
		em.flush();
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}
}

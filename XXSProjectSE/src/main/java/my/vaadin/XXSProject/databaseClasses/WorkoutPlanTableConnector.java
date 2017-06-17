package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import my.vaadin.XXSProject.databaseEntities.WorkoutPlan;

public class WorkoutPlanTableConnector implements WorkoutPlanTableConnectorInterface {

	@Override
	public List<WorkoutPlan> getWorkoutplansForUsername(String username) {
		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		List<WorkoutPlan> databasePlans = em
				.createQuery("SELECT w FROM workoutplans w WHERE w.fkUsername = '" + username + "'").getResultList();

		return databasePlans;
	}

	@Override
	public void addWorkoutPlanForUsername(WorkoutPlan newWorkout) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(newWorkout);
		em.getTransaction().commit();

		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public void deleteWorkoutPlanForUserName(String username, String planName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.createQuery(
				"DELETE FROM workoutplans w WHERE w.fkUsername = '" + username + "' AND w.name='" + planName + "'")
				.executeUpdate();
		em.getTransaction().commit();

		em.clear();
		em.close();
		emf.close();
	}
}

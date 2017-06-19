package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import my.vaadin.XXSProject.databaseEntities.Log;

public class LogTableConnector implements LogTableConnectorInterface {

	@Override
	public List<Log> getLogsForUsernameWorkoutPlanExercise(String username, String workoutPlanName,
			String exerciseName) {
		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		List<Log> databaseLogs = em.createQuery("SELECT l FROM logs l WHERE l.fkUsername = '" + username
				+ "' AND l.fkWorkoutplan='" + workoutPlanName + "' AND l.fkExercisename='" + exerciseName + "'")
				.getResultList();

		em.clear();
		em.close();
		emf.close();

		return databaseLogs;
	}

	@Override
	public void addLog(Log logToAdd) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(logToAdd);
		em.getTransaction().commit();

		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public void deleteLogsForUsernameAndWorkOutPlan(String username, String workoutPlan) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.createQuery(
				"DELETE FROM logs l WHERE l.fkUsername = '" + username + "' AND l.fkWorkoutplan='" + workoutPlan + "'")
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void deleteLogsForUsernameAndWorkoutPlanAndExercise(String username, String workoutPlan, String Exercise) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.createQuery("DELETE FROM logs l WHERE l.fkUsername = '" + username + "' AND l.fkWorkoutplan='" + workoutPlan
				+ "' AND l.fkExercisename='" + Exercise + "'").executeUpdate();
		em.getTransaction().commit();
		em.clear();
		em.close();
		emf.close();
	}

}

package my.vaadin.XXSProject.databaseClasses;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import my.vaadin.XXSProject.databaseEntities.Exercise;

public class ExercisesTableConnector implements ExercisesTableConnectorInterface {

	@Override
	public List<Exercise> getExercicesForUsersWorkoutPlan(String username, String workoutplanName) {
		// Datenbank-Zugriff via JPA zum Datenabgleich
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		List<Exercise> databaseExercises = em.createQuery("SELECT e FROM exercises e WHERE e.fkUsername = '" + username
				+ "' AND e.fkWorkoutplanName = '" + workoutplanName + "'").getResultList();
		
		return databaseExercises;
	}

	@Override
	public void addExerciseToPlan(Exercise newExercise) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(newExercise);
		em.getTransaction().commit();
		
		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public void deleteExerciseForUsernameAndPlan(String username, String planName, String exerciseName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.createQuery("DELETE FROM exercises e WHERE e.fkUsername = '" + username + "' AND e.fkWorkoutplanName='"
				+ planName + "' AND e.name='" + exerciseName + "'").executeUpdate();
		em.getTransaction().commit();

		em.clear();
		em.close();
		emf.close();
	}

	@Override
	public void deleteExercisesForUsernameAndPlan(String username, String planName) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("XXSDatenbankService");

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.createQuery("DELETE FROM exercises e WHERE e.fkUsername = '" + username + "' AND e.fkWorkoutplanName='"+ planName+"'").executeUpdate();
		em.getTransaction().commit();

		em.clear();
		em.close();
		emf.close();
	}
}

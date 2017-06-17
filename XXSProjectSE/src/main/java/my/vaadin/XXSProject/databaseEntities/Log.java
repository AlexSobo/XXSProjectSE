package my.vaadin.XXSProject.databaseEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "logs")
@Table(name = "logs")
public class Log {
	@Id
	private java.sql.Date date;
	private int reps;
	private int weight;
	private int sets;
	@Column(name = "fk_username")
	private String fkUsername;
	@Column(name = "fk_workoutplan")
	private String fkWorkoutplan;
	@Column(name = "fk_exercisename")
	private String fkExercisename;

	public Log() {
	}

	public Log(java.sql.Date date, int reps, int weight, int sets, String fkUsername, String fkWorkoutplan,
			String fkExercisename) {
		super();
		this.date = date;
		this.reps = reps;
		this.weight = weight;
		this.sets = sets;
		this.fkUsername = fkUsername;
		this.fkWorkoutplan = fkWorkoutplan;
		this.fkExercisename = fkExercisename;
	}

	// Getter und Setter
	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public String getFkUsername() {
		return fkUsername;
	}

	public void setFkUsername(String fkUsername) {
		this.fkUsername = fkUsername;
	}

	public String getFkWorkoutplan() {
		return fkWorkoutplan;
	}

	public void setFkWorkoutplan(String fkWorkoutplan) {
		this.fkWorkoutplan = fkWorkoutplan;
	}

	public String getFkExercisename() {
		return fkExercisename;
	}

	public void setFkExercisename(String fkExercisename) {
		this.fkExercisename = fkExercisename;
	}
}

package my.vaadin.XXSProject.databaseEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "exercises")
@Table(name = "exercises")
public class Exercise {
	@Id
	private String name;
	@Column(name = "target_reps")
	private int targetReps;
	@Column(name = "target_weight")
	private int targetWeight;
	@Column(name = "target_sets")
	private int targetSets;
	@Column(name = "fk_username")
	private String fkUsername;
	@Column(name = "fk_workoutplan_name")
	private String fkWorkoutplanName;

	public Exercise() {
	}

	public Exercise(String name, int targetReps, int targetWeight, int targetSets, String fkUsername,
			String fkWorkoutplanName) {
		super();
		this.name = name;
		this.targetReps = targetReps;
		this.targetWeight = targetWeight;
		this.targetSets = targetSets;
		this.fkUsername = fkUsername;
		this.fkWorkoutplanName = fkWorkoutplanName;
	}

	// Getter und Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTargetReps() {
		return targetReps;
	}

	public void setTargetReps(int targetReps) {
		this.targetReps = targetReps;
	}

	public int getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(int targetWeight) {
		this.targetWeight = targetWeight;
	}

	public int getTargetSets() {
		return targetSets;
	}

	public void setTargetSets(int targetSets) {
		this.targetSets = targetSets;
	}

	public String getFkUsername() {
		return fkUsername;
	}

	public void setFkUsername(String fkUsername) {
		this.fkUsername = fkUsername;
	}

	public String getFkWorkoutplanName() {
		return fkWorkoutplanName;
	}

	public void setFkWorkoutplanName(String fkWorkoutplanName) {
		this.fkWorkoutplanName = fkWorkoutplanName;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}

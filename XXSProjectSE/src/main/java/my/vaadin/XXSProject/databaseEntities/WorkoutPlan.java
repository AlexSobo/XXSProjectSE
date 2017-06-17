package my.vaadin.XXSProject.databaseEntities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "workoutplans")
@Table(name = "workoutplans")
public class WorkoutPlan {

	@Id
	private String name;
	private String description;
	@Column(name = "fk_username")
	private String fkUsername;

	public WorkoutPlan() {
	}

	public WorkoutPlan(String name, String description, String fkUsername) {
		this.name = name;
		this.description = description;
		this.fkUsername = fkUsername;
	}

	// Getter und Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFkUsername() {
		return fkUsername;
	}

	public void setFkUsername(String fkUsername) {
		this.fkUsername = fkUsername;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
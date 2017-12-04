package de.cit.backend.persistence.model;
// Generated 04.12.2017 14:01:29 by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserProject generated by hbm2java
 */
@Entity
@Table(name = "USER_PROJECT", catalog = "citBitDB", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_ID",
		"PROJECT_ID" }))
public class UserProject implements java.io.Serializable {

	private Integer id;
	private Project project;
	private Userdata userdata;

	public UserProject() {
	}

	public UserProject(Project project, Userdata userdata) {
		this.project = project;
		this.userdata = userdata;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public Userdata getUserdata() {
		return this.userdata;
	}

	public void setUserdata(Userdata userdata) {
		this.userdata = userdata;
	}

}

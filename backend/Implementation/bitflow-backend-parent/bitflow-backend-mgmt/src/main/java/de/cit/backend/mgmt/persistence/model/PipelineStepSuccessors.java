package de.cit.backend.mgmt.persistence.model;
// Generated 10.12.2017 16:49:45 by Hibernate Tools 5.2.3.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * PipelineStepSuccessors generated by hbm2java
 */
@Entity
@Table(name = "PIPELINE_STEP_SUCCESSORS", catalog = "citBitDB", uniqueConstraints = @UniqueConstraint(columnNames = {
		"STEP_ID", "SUCCESSOR_ID" }))
public class PipelineStepSuccessors extends BaseIdEntity implements java.io.Serializable {

	private Integer id;
	private PipelineStepDTO pipelineStepByStepId;
	private PipelineStepDTO pipelineStepBySuccessorId;

	public PipelineStepSuccessors() {
	}

	public PipelineStepSuccessors(PipelineStepDTO pipelineStepByStepId, PipelineStepDTO pipelineStepBySuccessorId) {
		this.pipelineStepByStepId = pipelineStepByStepId;
		this.pipelineStepBySuccessorId = pipelineStepBySuccessorId;
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
	@JoinColumn(name = "STEP_ID", nullable = false)
	public PipelineStepDTO getPipelineStepByStepId() {
		return this.pipelineStepByStepId;
	}

	public void setPipelineStepByStepId(PipelineStepDTO pipelineStepByStepId) {
		this.pipelineStepByStepId = pipelineStepByStepId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUCCESSOR_ID", nullable = false)
	public PipelineStepDTO getPipelineStepBySuccessorId() {
		return this.pipelineStepBySuccessorId;
	}

	public void setPipelineStepBySuccessorId(PipelineStepDTO pipelineStepBySuccessorId) {
		this.pipelineStepBySuccessorId = pipelineStepBySuccessorId;
	}

}
package de.cit.backend.mgmt.persistence.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.cit.backend.mgmt.persistence.model.enums.PipelineStateEnum;

@Entity
@Table(name = "PIPELINE_HISTORY", catalog = "citBitDB")
public class PipelineHistoryDTO extends BaseIdEntity implements java.io.Serializable {

	private Integer id;
	private PipelineDTO pipeline;
	private Date startedAt;
	private Date finishedAt;
	private PipelineStateEnum status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="PIPELINE_ID", nullable=false)
	public PipelineDTO getPipeline() {
		return pipeline;
	}

	public void setPipeline(PipelineDTO pipeline) {
		this.pipeline = pipeline;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTED_AT", nullable = true, length = 19)
	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISHED_AT", nullable = true, length = 19)
	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = true, length = 32)
	public PipelineStateEnum getStatus() {
		return status;
	}

	public void setStatus(PipelineStateEnum status) {
		this.status = status;
	}
}

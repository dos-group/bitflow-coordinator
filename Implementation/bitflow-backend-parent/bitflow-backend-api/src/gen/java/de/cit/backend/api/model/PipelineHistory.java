package de.cit.backend.api.model;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-27T00:00:27.767+01:00")
public class PipelineHistory   {
  
  private Integer ID = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    FAILED("failed"),

        FINISHED("finished"),

        RUNNING("running"),

        TERMINATED("terminated");
    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

  private StatusEnum status = null;
  private Date startedAt = null;
  private Date finishedAt = null;
  private Double duration = null;

  /**
   **/
  
  @ApiModelProperty(example = "5", value = "")
  @JsonProperty("ID")
  public Integer getID() {
    return ID;
  }
  public void setID(Integer ID) {
    this.ID = ID;
  }

  /**
   **/
  
  @ApiModelProperty(example = "finished", value = "")
  @JsonProperty("Status")
  public StatusEnum getStatus() {
    return status;
  }
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("StartedAt")
  public Date getStartedAt() {
    return startedAt;
  }
  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("FinishedAt")
  public Date getFinishedAt() {
    return finishedAt;
  }
  public void setFinishedAt(Date finishedAt) {
    this.finishedAt = finishedAt;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("Duration")
  public Double getDuration() {
    return duration;
  }
  public void setDuration(Double duration) {
    this.duration = duration;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineHistory pipelineHistory = (PipelineHistory) o;
    return Objects.equals(ID, pipelineHistory.ID) &&
        Objects.equals(status, pipelineHistory.status) &&
        Objects.equals(startedAt, pipelineHistory.startedAt) &&
        Objects.equals(finishedAt, pipelineHistory.finishedAt) &&
        Objects.equals(duration, pipelineHistory.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, status, startedAt, finishedAt, duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineHistory {\n");
    
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    finishedAt: ").append(toIndentedString(finishedAt)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.PipelineStep;
import de.cit.backend.api.model.Project;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public class Pipeline   {
  
  private Integer ID = null;
  private String name = null;
  private Project project = null;
  private String sript = null;
  private Date lastChanged = null;
  private List<PipelineStep> pipelineSteps = new ArrayList<PipelineStep>();

  /**
   **/
  
  @ApiModelProperty(example = "10", value = "")
  @JsonProperty("ID")
  public Integer getID() {
    return ID;
  }
  public void setID(Integer ID) {
    this.ID = ID;
  }

  /**
   **/
  
  @ApiModelProperty(example = "Example Pipeline", value = "")
  @JsonProperty("Name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("Project")
  public Project getProject() {
    return project;
  }
  public void setProject(Project project) {
    this.project = project;
  }

  /**
   **/
  
  @ApiModelProperty(example = "Dies -> Das -> Hier -> Und -> Da", value = "")
  @JsonProperty("Sript")
  public String getSript() {
    return sript;
  }
  public void setSript(String sript) {
    this.sript = sript;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("LastChanged")
  public Date getLastChanged() {
    return lastChanged;
  }
  public void setLastChanged(Date lastChanged) {
    this.lastChanged = lastChanged;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("PipelineSteps")
  public List<PipelineStep> getPipelineSteps() {
    return pipelineSteps;
  }
  public void setPipelineSteps(List<PipelineStep> pipelineSteps) {
    this.pipelineSteps = pipelineSteps;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pipeline pipeline = (Pipeline) o;
    return Objects.equals(ID, pipeline.ID) &&
        Objects.equals(name, pipeline.name) &&
        Objects.equals(project, pipeline.project) &&
        Objects.equals(sript, pipeline.sript) &&
        Objects.equals(lastChanged, pipeline.lastChanged) &&
        Objects.equals(pipelineSteps, pipeline.pipelineSteps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, name, project, sript, lastChanged, pipelineSteps);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Pipeline {\n");
    
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    project: ").append(toIndentedString(project)).append("\n");
    sb.append("    sript: ").append(toIndentedString(sript)).append("\n");
    sb.append("    lastChanged: ").append(toIndentedString(lastChanged)).append("\n");
    sb.append("    pipelineSteps: ").append(toIndentedString(pipelineSteps)).append("\n");
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


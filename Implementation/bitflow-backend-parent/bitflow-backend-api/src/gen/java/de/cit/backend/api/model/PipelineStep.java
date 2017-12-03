package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.PipelineStep;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public class PipelineStep   {
  
  private Integer ID = null;
  private Integer pipelineId = null;
  private String algorithm = null;
  private List<PipelineStep> successors = new ArrayList<PipelineStep>();

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
  
  @ApiModelProperty(example = "10", value = "")
  @JsonProperty("PipelineId")
  public Integer getPipelineId() {
    return pipelineId;
  }
  public void setPipelineId(Integer pipelineId) {
    this.pipelineId = pipelineId;
  }

  /**
   **/
  
  @ApiModelProperty(example = "Avg", value = "")
  @JsonProperty("Algorithm")
  public String getAlgorithm() {
    return algorithm;
  }
  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  /**
   **/
  
  @ApiModelProperty(example = "[]", value = "")
  @JsonProperty("Successors")
  public List<PipelineStep> getSuccessors() {
    return successors;
  }
  public void setSuccessors(List<PipelineStep> successors) {
    this.successors = successors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PipelineStep pipelineStep = (PipelineStep) o;
    return Objects.equals(ID, pipelineStep.ID) &&
        Objects.equals(pipelineId, pipelineStep.pipelineId) &&
        Objects.equals(algorithm, pipelineStep.algorithm) &&
        Objects.equals(successors, pipelineStep.successors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, pipelineId, algorithm, successors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineStep {\n");
    
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    pipelineId: ").append(toIndentedString(pipelineId)).append("\n");
    sb.append("    algorithm: ").append(toIndentedString(algorithm)).append("\n");
    sb.append("    successors: ").append(toIndentedString(successors)).append("\n");
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


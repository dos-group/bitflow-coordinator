package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-02-01T00:26:34.119+01:00")
public class PartialDeployment   {
  
  private String agentAdress = null;
  private Integer pipelineIdOnAgent = null;
  private String partialScript = null;

  /**
   **/
  
  @ApiModelProperty(example = "127.0.0.1:8081", value = "")
  @JsonProperty("AgentAdress")
  public String getAgentAdress() {
    return agentAdress;
  }
  public void setAgentAdress(String agentAdress) {
    this.agentAdress = agentAdress;
  }

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("PipelineIdOnAgent")
  public Integer getPipelineIdOnAgent() {
    return pipelineIdOnAgent;
  }
  public void setPipelineIdOnAgent(Integer pipelineIdOnAgent) {
    this.pipelineIdOnAgent = pipelineIdOnAgent;
  }

  /**
   **/
  
  @ApiModelProperty(example = "input.csv -> avg() -> 127.0.0.1:60001", value = "")
  @JsonProperty("PartialScript")
  public String getPartialScript() {
    return partialScript;
  }
  public void setPartialScript(String partialScript) {
    this.partialScript = partialScript;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartialDeployment partialDeployment = (PartialDeployment) o;
    return Objects.equals(agentAdress, partialDeployment.agentAdress) &&
        Objects.equals(pipelineIdOnAgent, partialDeployment.pipelineIdOnAgent) &&
        Objects.equals(partialScript, partialDeployment.partialScript);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentAdress, pipelineIdOnAgent, partialScript);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartialDeployment {\n");
    
    sb.append("    agentAdress: ").append(toIndentedString(agentAdress)).append("\n");
    sb.append("    pipelineIdOnAgent: ").append(toIndentedString(pipelineIdOnAgent)).append("\n");
    sb.append("    partialScript: ").append(toIndentedString(partialScript)).append("\n");
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


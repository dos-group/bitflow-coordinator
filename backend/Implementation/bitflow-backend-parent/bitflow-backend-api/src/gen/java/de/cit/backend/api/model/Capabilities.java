package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.Capability;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class Capabilities   {
  
  private Long agentId = null;
  private List<Capability> capabilities = new ArrayList<Capability>();

  /**
   **/
  
  @ApiModelProperty(example = "1", value = "")
  @JsonProperty("AgentId")
  public Long getAgentId() {
    return agentId;
  }
  public void setAgentId(Long agentId) {
    this.agentId = agentId;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("Capabilities")
  public List<Capability> getCapabilities() {
    return capabilities;
  }
  public void setCapabilities(List<Capability> capabilities) {
    this.capabilities = capabilities;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Capabilities capabilities = (Capabilities) o;
    return Objects.equals(agentId, capabilities.agentId) &&
        Objects.equals(capabilities, capabilities.capabilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentId, capabilities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Capabilities {\n");
    
    sb.append("    agentId: ").append(toIndentedString(agentId)).append("\n");
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
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


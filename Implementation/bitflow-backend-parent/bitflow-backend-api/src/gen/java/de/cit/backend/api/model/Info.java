package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.Agent;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-04T15:16:54.751+01:00")
public class Info   {
  
  private Integer numberOfAgents = null;
  private Integer numberOfOnlineAgents = null;
  private Integer numberOfOfflineAgents = null;
  private List<Agent> agents = new ArrayList<Agent>();

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("NumberOfAgents")
  public Integer getNumberOfAgents() {
    return numberOfAgents;
  }
  public void setNumberOfAgents(Integer numberOfAgents) {
    this.numberOfAgents = numberOfAgents;
  }

  /**
   **/

  @ApiModelProperty(example = "3", value = "")
  @JsonProperty("NumberOfOnlineAgents")
  public Integer getNumberOfOnlineAgents() {
    return numberOfOnlineAgents;
  }
  public void setNumberOfOnlineAgents(Integer numberOfOnlineAgents) {
    this.numberOfOnlineAgents = numberOfOnlineAgents;
  }

  /**
   **/

  @ApiModelProperty(example = "3", value = "")
  @JsonProperty("NumberOfOfflineAgents")
  public Integer getNumberOfOfflineAgents() {
    return numberOfOfflineAgents;
  }
  public void setNumberOfOfflineAgents(Integer numberOfOfflineAgents) {
    this.numberOfOfflineAgents = numberOfOfflineAgents;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("Agents")
  public List<Agent> getAgents() {
    return agents;
  }
  public void setAgents(List<Agent> agents) {
    this.agents = agents;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Info info = (Info) o;
    return Objects.equals(numberOfAgents, info.numberOfAgents) &&
            Objects.equals(numberOfOnlineAgents, info.numberOfOnlineAgents) &&
            Objects.equals(numberOfOfflineAgents, info.numberOfOfflineAgents) &&
        Objects.equals(agents, info.agents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfAgents, numberOfOnlineAgents, numberOfOfflineAgents, agents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Info {\n");
    
    sb.append("    numberOfAgents: ").append(toIndentedString(numberOfAgents)).append("\n");
    sb.append("    numberOfOnlineAgents: ").append(toIndentedString(numberOfOnlineAgents)).append("\n");
    sb.append("    numberOfOfflineAgents: ").append(toIndentedString(numberOfOfflineAgents)).append("\n");
    sb.append("    agents: ").append(toIndentedString(agents)).append("\n");
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


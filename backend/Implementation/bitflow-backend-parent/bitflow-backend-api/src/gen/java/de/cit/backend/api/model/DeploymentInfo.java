package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.PartialDeployment;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-02-01T00:26:34.119+01:00")
public class DeploymentInfo   {
  
  private String status = null;
  private Integer historyID = null;
  private List<PartialDeployment> partialDeployments = new ArrayList<PartialDeployment>();

  /**
   **/
  
  @ApiModelProperty(example = "running", value = "")
  @JsonProperty("Status")
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("HistoryID")
  public Integer getHistoryID() {
    return historyID;
  }
  public void setHistoryID(Integer historyID) {
    this.historyID = historyID;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("PartialDeployments")
  public List<PartialDeployment> getPartialDeployments() {
    return partialDeployments;
  }
  public void setPartialDeployments(List<PartialDeployment> partialDeployments) {
    this.partialDeployments = partialDeployments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeploymentInfo deploymentInfo = (DeploymentInfo) o;
    return Objects.equals(status, deploymentInfo.status) &&
        Objects.equals(historyID, deploymentInfo.historyID) &&
        Objects.equals(partialDeployments, deploymentInfo.partialDeployments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, historyID, partialDeployments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeploymentInfo {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    historyID: ").append(toIndentedString(historyID)).append("\n");
    sb.append("    partialDeployments: ").append(toIndentedString(partialDeployments)).append("\n");
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


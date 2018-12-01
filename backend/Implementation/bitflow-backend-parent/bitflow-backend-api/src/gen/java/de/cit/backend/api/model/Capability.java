package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-09T23:05:57.697+01:00")
public class Capability   {
  
  private String name = null;
  private Boolean isFork = null;
  private String description = null;
  private List<String> requiredParams = new ArrayList<String>();
  private List<String> optionalParams = new ArrayList<String>();

  /**
   **/
  
  @ApiModelProperty(example = "avg", value = "")
  @JsonProperty("Name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  
  @ApiModelProperty(example = "false", value = "")
  @JsonProperty("IsFork")
  public Boolean getIsFork() {
    return isFork;
  }
  public void setIsFork(Boolean isFork) {
    this.isFork = isFork;
  }

  /**
   **/
  
  @ApiModelProperty(example = "Add an average metric for every incoming metric. Optional parameter: duration or number of samples. Optional parameters: [window]", value = "")
  @JsonProperty("Description")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  
  @ApiModelProperty(example = "[]", value = "")
  @JsonProperty("RequiredParams")
  public List<String> getRequiredParams() {
    return requiredParams;
  }
  public void setRequiredParams(List<String> requiredParams) {
    this.requiredParams = requiredParams;
  }

  /**
   **/
  
  @ApiModelProperty(example = "[\"window\"]", value = "")
  @JsonProperty("OptionalParams")
  public List<String> getOptionalParams() {
    return optionalParams;
  }
  public void setOptionalParams(List<String> optionalParams) {
    this.optionalParams = optionalParams;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Capability capability = (Capability) o;
    return Objects.equals(name, capability.name) &&
        Objects.equals(isFork, capability.isFork) &&
        Objects.equals(description, capability.description) &&
        Objects.equals(requiredParams, capability.requiredParams) &&
        Objects.equals(optionalParams, capability.optionalParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, isFork, description, requiredParams, optionalParams);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Capability {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isFork: ").append(toIndentedString(isFork)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    requiredParams: ").append(toIndentedString(requiredParams)).append("\n");
    sb.append("    optionalParams: ").append(toIndentedString(optionalParams)).append("\n");
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


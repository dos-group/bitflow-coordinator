package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public class Tag   {
  
  private String resources = null;
  private String slots = null;

  /**
   **/
  
  @ApiModelProperty(example = "medium", value = "")
  @JsonProperty("resources")
  public String getResources() {
    return resources;
  }
  public void setResources(String resources) {
    this.resources = resources;
  }

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("slots")
  public String getSlots() {
    return slots;
  }
  public void setSlots(String slots) {
    this.slots = slots;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tag tag = (Tag) o;
    return Objects.equals(resources, tag.resources) &&
        Objects.equals(slots, tag.slots);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resources, slots);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Tag {\n");
    
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    slots: ").append(toIndentedString(slots)).append("\n");
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


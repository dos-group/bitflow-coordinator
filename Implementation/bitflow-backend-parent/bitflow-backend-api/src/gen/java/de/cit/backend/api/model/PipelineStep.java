package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class PipelineStep   {
  
  private Integer ID = null;
  private Integer number = null;
  private Integer agentId = null;

  /**
   * Gets or Sets typ
   */
  public enum TypEnum {
    SOURCE("source"),

        SINK("sink"),

        OPERATION("operation");
    private String value;

    TypEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

  private TypEnum typ = null;
  private String content = null;
  private List<Object> params = new ArrayList<Object>();
  private List<Integer> successors = new ArrayList<Integer>();

  /**
   * ID from database
   **/
  
  @ApiModelProperty(example = "10012", value = "ID from database")
  @JsonProperty("ID")
  public Integer getID() {
    return ID;
  }
  public void setID(Integer ID) {
    this.ID = ID;
  }

  /**
   * pipeline-internal identifier. Must be unique only inside one pipeline
   **/
  
  @ApiModelProperty(example = "2", value = "pipeline-internal identifier. Must be unique only inside one pipeline")
  @JsonProperty("Number")
  public Integer getNumber() {
    return number;
  }
  public void setNumber(Integer number) {
    this.number = number;
  }

  /**
   **/
  
  @ApiModelProperty(example = "15", value = "")
  @JsonProperty("AgentId")
  public Integer getAgentId() {
    return agentId;
  }
  public void setAgentId(Integer agentId) {
    this.agentId = agentId;
  }

  /**
   **/
  
  @ApiModelProperty(example = "operation", value = "")
  @JsonProperty("Typ")
  public TypEnum getTyp() {
    return typ;
  }
  public void setTyp(TypEnum typ) {
    this.typ = typ;
  }

  /**
   * actual operation to be executed by that pipeline step; or a source/sink definition
   **/
  
  @ApiModelProperty(example = "avg", value = "actual operation to be executed by that pipeline step; or a source/sink definition")
  @JsonProperty("Content")
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * list of key-value pairs
   **/
  
  @ApiModelProperty(example = "[{\"paramKey\":\"paramValue\"}]", value = "list of key-value pairs")
  @JsonProperty("Params")
  public List<Object> getParams() {
    return params;
  }
  public void setParams(List<Object> params) {
    this.params = params;
  }

  /**
   * list of immediate successor steps, referencing the Number field, NOT the ID
   **/
  
  @ApiModelProperty(example = "[3,4]", value = "list of immediate successor steps, referencing the Number field, NOT the ID")
  @JsonProperty("Successors")
  public List<Integer> getSuccessors() {
    return successors;
  }
  public void setSuccessors(List<Integer> successors) {
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
        Objects.equals(number, pipelineStep.number) &&
        Objects.equals(agentId, pipelineStep.agentId) &&
        Objects.equals(typ, pipelineStep.typ) &&
        Objects.equals(content, pipelineStep.content) &&
        Objects.equals(params, pipelineStep.params) &&
        Objects.equals(successors, pipelineStep.successors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, number, agentId, typ, content, params, successors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PipelineStep {\n");
    
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    agentId: ").append(toIndentedString(agentId)).append("\n");
    sb.append("    typ: ").append(toIndentedString(typ)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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


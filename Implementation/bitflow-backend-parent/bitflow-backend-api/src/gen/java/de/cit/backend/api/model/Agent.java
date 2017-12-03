package de.cit.backend.api.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import de.cit.backend.api.model.Tag;
import java.util.List;
import javax.validation.constraints.*;
import io.swagger.annotations.*;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2017-12-03T19:12:56.421+01:00")
public class Agent   {
  
  private String hostname = null;
  private Tag tags = null;
  private Integer numCores = null;
  private Long totalMem = null;
  private List<Double> usedCpuCores = new ArrayList<Double>();
  private Double usedCpu = null;
  private Long usedMem = null;
  private Integer numProcs = null;
  private Integer goroutines = null;

  /**
   **/
  
  @ApiModelProperty(example = "worker12", value = "")
  @JsonProperty("Hostname")
  public String getHostname() {
    return hostname;
  }
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("Tags")
  public Tag getTags() {
    return tags;
  }
  public void setTags(Tag tags) {
    this.tags = tags;
  }

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("NumCores")
  public Integer getNumCores() {
    return numCores;
  }
  public void setNumCores(Integer numCores) {
    this.numCores = numCores;
  }

  /**
   **/
  
  @ApiModelProperty(example = "456464", value = "")
  @JsonProperty("TotalMem")
  public Long getTotalMem() {
    return totalMem;
  }
  public void setTotalMem(Long totalMem) {
    this.totalMem = totalMem;
  }

  /**
   **/
  
  @ApiModelProperty(example = "[2.3,4.6,3.4567]", value = "")
  @JsonProperty("UsedCpuCores")
  public List<Double> getUsedCpuCores() {
    return usedCpuCores;
  }
  public void setUsedCpuCores(List<Double> usedCpuCores) {
    this.usedCpuCores = usedCpuCores;
  }

  /**
   **/
  
  @ApiModelProperty(example = "2.3", value = "")
  @JsonProperty("UsedCpu")
  public Double getUsedCpu() {
    return usedCpu;
  }
  public void setUsedCpu(Double usedCpu) {
    this.usedCpu = usedCpu;
  }

  /**
   **/
  
  @ApiModelProperty(example = "65243", value = "")
  @JsonProperty("UsedMem")
  public Long getUsedMem() {
    return usedMem;
  }
  public void setUsedMem(Long usedMem) {
    this.usedMem = usedMem;
  }

  /**
   **/
  
  @ApiModelProperty(example = "247", value = "")
  @JsonProperty("NumProcs")
  public Integer getNumProcs() {
    return numProcs;
  }
  public void setNumProcs(Integer numProcs) {
    this.numProcs = numProcs;
  }

  /**
   **/
  
  @ApiModelProperty(example = "6", value = "")
  @JsonProperty("Goroutines")
  public Integer getGoroutines() {
    return goroutines;
  }
  public void setGoroutines(Integer goroutines) {
    this.goroutines = goroutines;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Agent agent = (Agent) o;
    return Objects.equals(hostname, agent.hostname) &&
        Objects.equals(tags, agent.tags) &&
        Objects.equals(numCores, agent.numCores) &&
        Objects.equals(totalMem, agent.totalMem) &&
        Objects.equals(usedCpuCores, agent.usedCpuCores) &&
        Objects.equals(usedCpu, agent.usedCpu) &&
        Objects.equals(usedMem, agent.usedMem) &&
        Objects.equals(numProcs, agent.numProcs) &&
        Objects.equals(goroutines, agent.goroutines);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostname, tags, numCores, totalMem, usedCpuCores, usedCpu, usedMem, numProcs, goroutines);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Agent {\n");
    
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    numCores: ").append(toIndentedString(numCores)).append("\n");
    sb.append("    totalMem: ").append(toIndentedString(totalMem)).append("\n");
    sb.append("    usedCpuCores: ").append(toIndentedString(usedCpuCores)).append("\n");
    sb.append("    usedCpu: ").append(toIndentedString(usedCpu)).append("\n");
    sb.append("    usedMem: ").append(toIndentedString(usedMem)).append("\n");
    sb.append("    numProcs: ").append(toIndentedString(numProcs)).append("\n");
    sb.append("    goroutines: ").append(toIndentedString(goroutines)).append("\n");
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


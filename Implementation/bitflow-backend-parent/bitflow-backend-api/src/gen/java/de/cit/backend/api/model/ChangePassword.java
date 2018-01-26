package de.cit.backend.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-27T00:00:27.767+01:00")
public class ChangePassword   {
  
  private String oldPassword = null;
  private String newPassword = null;

  /**
   **/
  
  @ApiModelProperty(example = "oldPassword", value = "")
  @JsonProperty("oldPassword")
  public String getOldPassword() {
    return oldPassword;
  }
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  /**
   **/
  
  @ApiModelProperty(example = "newPassword", value = "")
  @JsonProperty("newPassword")
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChangePassword changePassword = (ChangePassword) o;
    return Objects.equals(oldPassword, changePassword.oldPassword) &&
        Objects.equals(newPassword, changePassword.newPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldPassword, newPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChangePassword {\n");
    
    sb.append("    oldPassword: ").append(toIndentedString(oldPassword)).append("\n");
    sb.append("    newPassword: ").append(toIndentedString(newPassword)).append("\n");
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


package de.cit.backend.api.model;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-27T00:00:27.767+01:00")
public class User   {
  
  private Integer ID = null;
  private String name = null;
  private String email = null;
  private Date registeredSince = null;
  private String password = null;

  /**
   **/
  
  @ApiModelProperty(example = "5", value = "")
  @JsonProperty("ID")
  public Integer getID() {
    return ID;
  }
  public void setID(Integer ID) {
    this.ID = ID;
  }

  /**
   **/
  
  @ApiModelProperty(example = "Achmed Schachbrett", value = "")
  @JsonProperty("Name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  
  @ApiModelProperty(example = "achmed.schachbrett@test.de", value = "")
  @JsonProperty("Email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("RegisteredSince")
  public Date getRegisteredSince() {
    return registeredSince;
  }
  public void setRegisteredSince(Date registeredSince) {
    this.registeredSince = registeredSince;
  }

  /**
   * This is only to be used when creating a new user. If sending it with another request it will be ignored. This field will never be returned from backend.
   **/
  
  @ApiModelProperty(example = "pwd", value = "This is only to be used when creating a new user. If sending it with another request it will be ignored. This field will never be returned from backend.")
  @JsonProperty("Password")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(ID, user.ID) &&
        Objects.equals(name, user.name) &&
        Objects.equals(email, user.email) &&
        Objects.equals(registeredSince, user.registeredSince) &&
        Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ID, name, email, registeredSince, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    ID: ").append(toIndentedString(ID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    registeredSince: ").append(toIndentedString(registeredSince)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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


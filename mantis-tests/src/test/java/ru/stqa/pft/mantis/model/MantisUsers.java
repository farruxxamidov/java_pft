package ru.stqa.pft.mantis.model;

/**
 * Created by Farrukh on 21-Apr-16.
 */
public class MantisUsers {

  private String username;
  private String password;
  private String email;

  public String getUsername() {
    return username;
  }

  public MantisUsers withUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public MantisUsers withPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public MantisUsers withEmail(String email) {
    this.email = email;
    return this;
  }

}

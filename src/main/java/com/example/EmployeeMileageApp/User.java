/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EmployeeMileageApp;

/**
 *
 * @author Gerry
 */
public class User {
  private int id;
  private String username;
  private String password;
  private String usertype;
  
  protected User(int id, String username, String password, String usertype){
      
  }
   
  protected User(){
    this.id = id;
    this.username = username;
    this.password = password;
    this.usertype = usertype;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id){
      this.id = id;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username){
      this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password){
      this.password = password;
  }
  
  public String getUserType() {
    return usertype;
  }
  
  public void setUserType(String usertype){
      this.usertype = usertype;
  }
}

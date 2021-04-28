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
public class Employee {

  private String firstname;
  private String lastname;
  private String middlename;
  private String emailaddress;
  private String employeenumber;
  private int userid;
  private String department;
  
  protected Employee(){
      
  }
   
  protected Employee(String firstname, String lastname, String middlename, String emailaddress, String employeenumber, String department){
    this.firstname = firstname;
    this.lastname = lastname;
    this.middlename = middlename;
    this.emailaddress = emailaddress;
    this.employeenumber = employeenumber;
    this.department = department;
  }
  
  public String getFirstname() {
    return firstname;
  }
  
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }
  
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  
  public String getMiddlename() {
    return middlename;
  }
  
  public void setMiddlename(String middlename) {
    this.middlename = middlename;
  }

  public String getEmailaddress() {
    return emailaddress;
  }

  public void setEmailaddress(String emailaddress) {
    this.emailaddress = emailaddress;
  }
  
  public String getEmployeenumber() {
    return employeenumber;
  }

  public void setEmployeenumber(String employeenumber) {
    this.employeenumber = employeenumber;
  }
  
  public int getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }
  
  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }
}

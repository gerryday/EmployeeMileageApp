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
public class Distance {
    private int id;
    private String startlocation;
    private String endlocation;
    private float miles;
    
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public String getStartLocation() {
    return startlocation;
  }

  public void setStartLocation(String startlocation) {
    this.startlocation = startlocation;
  }
  public String getEndLocation() {
    return endlocation;
  }

  public void setEndLocation(String endlocation) {
    this.endlocation = endlocation;
  }
  
  public float getMiles() {
    return miles;
  }

  public void setMiles(float miles) {
    this.miles = miles;
  }
}

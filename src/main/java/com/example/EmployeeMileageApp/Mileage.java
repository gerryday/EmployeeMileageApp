/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EmployeeMileageApp;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author Gerry
 */

public class Mileage {
    private int id;
    
    @NotEmpty
    private String date;
    
    @NotEmpty
    private String startlocation;
    @NotEmpty
    private String endlocation;
    
    private int workorderid;
    private String workorderdescription;
    private float miles;
    private float rate;
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public String getDate(){
        return date;
    }
            
    public void setDate(String date){
        this.date = date;
    }
    
    public String getStartLocation(){
        return startlocation;
    }
    public void setStartLocation(String startlocation){
        this.startlocation = startlocation;
    }
    public String getEndLocation(){
        return endlocation;
    }
    public void setEndLocation(String endlocation){
        this.endlocation = endlocation;
    }
    public int getWorkOrderId(){
        return workorderid;
    }
    public void setWorkOrderId(int workorderid){
        this.workorderid = workorderid;
    }
    public String getWorkOrderDescription(){
        return workorderdescription;
    }
    public void setWorkOrderDescription(String workorderdescription){
        this.workorderdescription = workorderdescription;
    }
    public float getMiles(){
        return miles; 
    }
    public void setMiles(float miles){
        this.miles = miles;
    }
    public float getRate(){
        return rate;
    }
    public void setRate(float rate){
        this.rate = rate;
    }
 }
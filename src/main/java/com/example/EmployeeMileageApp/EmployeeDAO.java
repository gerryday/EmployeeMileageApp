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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
  
@Repository
@Transactional
public class EmployeeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(Employee employee){
        String sqlInsert = "spAddEmployee ?,?,?,?,?,?,?,?";
        jdbcTemplate.update(sqlInsert, employee.getEmployeenumber(),
                                        employee.getFirstname(), 
                                        employee.getLastname(),
                                        employee.getMiddlename(),
                                        employee.getEmailaddress(),
                                        employee.getDepartment(),
                                        employee.getUserid(),
                                        1);
   };
   
   public Employee get(String employeenumber){
       String sql = "spGetEmployee ?";
       Object[] args = {employeenumber};
       Employee employee = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Employee.class));
        return employee;
   };
   
   public List<Employee> list(){
       String sql = "spGetEmployees";
       
       List<Employee> listEmployees = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Employee.class));
       
       return listEmployees;
   };
   
   public void delete(String employeenumber){
       String sql = "spDeleteEmployee ?,?";
       jdbcTemplate.update(sql, employeenumber, 1);
   };
   
   public void update(Employee employee){
       String sqlInsert = "spUpdateEmployee ?,?,?,?,?,?,?,?";   
        jdbcTemplate.update(sqlInsert, employee.getEmployeenumber(),
                                        employee.getFirstname(), 
                                        employee.getLastname(),
                                        employee.getMiddlename(),
                                        employee.getUserid(),
                                        employee.getEmailaddress(),
                                        employee.getDepartment(),                                     
                                      1);
 
   }
}
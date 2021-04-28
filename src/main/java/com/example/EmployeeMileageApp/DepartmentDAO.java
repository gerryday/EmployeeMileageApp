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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
  
@Repository
@Transactional
public class DepartmentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(Department department){
        String sqlInsert = "spAddDepartment ?,?";
        jdbcTemplate.update(sqlInsert, department.getName(),1);
   };
   
   public Department get(int id){
       String sql = "spGetDepartment ?";
       Object[] args = {id};
       Department department = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Department.class));
        return department;
   };
   
   public List<Department> list(){
       String sql = "spGetDepartments";
       
       List<Department> listDepartments = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Department.class));
       
       return listDepartments;
   };
   
   public void delete(int id){
       String sql = "spDeleteDepartment ?,?";
       jdbcTemplate.update(sql, id , 1);
   };
   
   public void update(Department department){
       String sql = "spUpdateDepartment ?,?,?";
       jdbcTemplate.update(sql, department.getId(),department.getName(),1);      
   }
}
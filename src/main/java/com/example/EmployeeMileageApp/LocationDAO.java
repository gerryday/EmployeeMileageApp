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
public class LocationDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(Location location){
        String sqlInsert = "spAddLocation ?,?,?,?,?,?,?";
        jdbcTemplate.update(sqlInsert, location.getName(), 
                                        location.getAddress1(),
                                        location.getAddress2(),
                                        location.getCity(),
                                        location.getState(),
                                        location.getZip(),
                                        1
                                        );
   };
   
   public Location get(int id){
       String sql = "spGetLocation ?";
       Object[] args = {id};
       Location location = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Location.class));
        return location;
   };
   
   public List<Location> list(){
       String sql = "spGetLocations";
       
       List<Location> listLocations = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Location.class));
       
       return listLocations;
   };
   
   public void delete(int id){
       String sql = "spDeleteLocation ?,?";
       jdbcTemplate.update(sql,id,1);
   };
   
   public void update(Location location){
       String sql = "spUpdateLocation ?,?,?,?,?,?,?,?";
       jdbcTemplate.update(sql,location.getId(),
                               location.getName(),
                               location.getAddress1(),
                               location.getAddress2(),
                               location.getCity(),
                               location.getState(),
                               location.getZip(),
                               1);  
   }
}
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
public class DistanceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(Distance distance){
        String sqlInsert = "spAddDistance ?,?,?,?";
        jdbcTemplate.update(sqlInsert, distance.getStartLocation(), 
                                        distance.getEndLocation(),
                                        distance.getMiles(),
                                        1
                                        );
   };
   
   public Distance get(int id){
       String sql = "spGetDistance ?";
       Object[] args = {id};
       Distance distance = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Distance.class));
        return distance;
   };
   
   public List<Distance> list(){
       String sql = "spGetDistances";
       
       List<Distance> listDistances = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Distance.class));
       
       return listDistances;
   };
   
   public void delete(int id){
       String sql = "spDeleteDistance ?,?";
       jdbcTemplate.update(sql, id, 1);
   };
   
   public void update(Distance distance){
       String sql = "spUpdateDistance ?,?,?";
       jdbcTemplate.update(sql, distance.getId(),
                                distance.getMiles(),
                                1);
   }
}
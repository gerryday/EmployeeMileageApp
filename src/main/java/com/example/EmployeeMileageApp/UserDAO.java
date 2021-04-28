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
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(User user){
        String sqlInsert = "spAddUser ?,?,?,?";
        jdbcTemplate.update(sqlInsert, user.getUsername(), 
                                        user.getPassword(),
                                        user.getUserType(),
                                        1
                                        );
   };
   
   public User get(int id){
       String sql = "spGetUser ?";
       Object[] args = {id};
       User user = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(User.class));
        return user;
   };
   public List list(){
       String sql = "spGetUsers";
       
       List<User> listUsers = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(User.class));
       
       return listUsers;
   };
   
   public List listTypes(){
       String sql = "spGetUserTypes";
       
       List<User> listUsertypes = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(User.class));
       
       return listUsertypes;
   };
   
   public void delete(int id){
       String sql = "spDeleteUser ?,?";
       jdbcTemplate.update(sql, id, 1);
       
   };
   
   public void update(User user){
       String sql = "spUpdateUser ?,?,?,?,?";
       jdbcTemplate.update(sql, user.getId(),
                                user.getUsername(), 
                                user.getPassword(),
                                user.getUserType(),
                                1); 
   }
}
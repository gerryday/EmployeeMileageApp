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
import java.text.NumberFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
  
@Repository
@Transactional
public class MileageDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
   /** 
      * This is the method to be used to initialize
      * database resources ie. connection.
   */
   //public setDataSource(DataSource ds);
   public void save(Mileage mileage){
        String sqlInsert = "spAddMileageEntry ?,?,?,?,?,?,?,?";
        jdbcTemplate.update(sqlInsert, 1,
                                       mileage.getDate(),
                                       mileage.getStartLocation(),
                                       mileage.getEndLocation(),
                                       mileage.getWorkOrderId(),
                                       mileage.getWorkOrderDescription(),
                                       1,
                                       1);
   };
   
   public Mileage get(int id){
       String sql = "spGetMileageEntry ?";
       Object[] args = {id};
       Mileage mileage = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Mileage.class));
        return mileage;
   };
   
   public double getMiles(int id){
       String sql = "spCalculateMileage ?";
       Object[] args = {id};
       double mileage = 0;
       try{
           mileage = jdbcTemplate.queryForObject(sql, args,Double.class);
       }catch(Exception e){
           mileage = 0;
       }
       return mileage;
   }
   public String getReimbursement(int id){
       String sql = "spCalculateReimbursement ?";
       Object[] args = {id};
       NumberFormat formatter = NumberFormat.getCurrencyInstance();
       double reimbursement = 0;
       
       try{
           reimbursement = jdbcTemplate.queryForObject(sql, args,Double.class);
       }catch(Exception e){
           reimbursement = 0;
       }
       
       String moneyString = formatter.format(reimbursement);
       return moneyString;
   }
   public List<Mileage> list(int userid){
       String sql = "spGetMileageEntries ?";
       Object[] args = {userid};
       List<Mileage> listMileageEntries = jdbcTemplate.query(sql,args,
                BeanPropertyRowMapper.newInstance(Mileage.class));
       
       return listMileageEntries;
   };
   
   public void delete(int id){
       String sql = "spDeleteMileageEntry ?,?";
       jdbcTemplate.update(sql, id, 1);
   };
   
   public void update(Mileage mileage){
       String sql = "spUpdateMileageEntry ?,?,?,?,?,?,?";
        jdbcTemplate.update(sql, mileage.getId(),
                                       mileage.getDate(),
                                       mileage.getStartLocation(),
                                       mileage.getEndLocation(),
                                       mileage.getWorkOrderId(),
                                       mileage.getWorkOrderDescription(),
                                       1);  
   }
}
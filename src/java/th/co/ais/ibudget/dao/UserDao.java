/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.ais.ibudget.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import th.co.ais.ibudget.db.DbConnection;
import th.co.ais.ibudget.model.User;

/**
 *
 * @author POOL_LAPTOP
 */
public class UserDao {
    
    final static Logger logger = Logger.getLogger(UserDao.class);
    
    private Connection conn = null;
    
    public User getUser(String username, String password) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT `id`, `username`, `password`, `fname`, `lname`, `gender`, `mobile`, `email`, ");
            sql.append(" `address`, `position`, `status`, `role`, `create_date`, `create_by` FROM `user` ");
            sql.append(" WHERE username = ? and password = md5(?)");
            
            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(0, username);
            pstm.setString(1, password);
            
            rs = pstm.executeQuery();
            if(rs.next()){
                user = this.getEntityUser(rs);
            }
        } catch (Exception e) {
            logger.error("getUser error", e);
        } finally {
            this.close(pstm, rs);
        }
        return user;
    }
    
    private User getEntityUser(ResultSet rs) throws SQLException{
        User user = new User();
        user.setAddress(rs.getString("address"));
        user.setCreateBy(rs.getInt("create_by"));
        user.setCreateDate(rs.getString("create_date"));
        user.setEmail(rs.getString("email"));
        user.setFname(rs.getString("fname"));
        user.setGender(rs.getString("gender"));
        user.setId(rs.getInt("id"));
        user.setLname(rs.getString("lname"));
        user.setMobile(rs.getString("mobile"));
        user.setPassword(rs.getString("password"));
        user.setPosition(rs.getString("position"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        user.setUsername(rs.getString("username"));
        return user;
    }
    
    public void close(PreparedStatement pstm, ResultSet rs) {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            logger.error("close db error", ex);
        }
    }
    
}

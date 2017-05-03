package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ConfigDao {

    final static Logger logger = Logger.getLogger(ConfigDao.class);

    private Connection conn = null;

    public Map<String, String> getConfigMap(String configCode) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        Map<String, String> configMap = new HashMap<String, String>();
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.conf_name,c.conf_value FROM config c WHERE c.conf_code = ?";
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, configCode);
            rs = pstm.executeQuery();
            while (rs.next()) {
                configMap.put(rs.getString("conf_name"), rs.getString("conf_value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getConfigMap error", e);
        } finally {
            this.close(pstm, rs);
        }
        return configMap;
    }

    public List<Config> getConfigList(String configCode) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Config> configList = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.conf_name,c.conf_value FROM config c WHERE c.conf_code = ? ORDER BY c.conf_value ASC";
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, configCode);
            rs = pstm.executeQuery();
            configList = new ArrayList<Config>();
            while (rs.next()) {
                Config config = new Config();
                config.setConfName(rs.getString("conf_name"));
                config.setConfValue(rs.getString("conf_value"));
                configList.add(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getConfigList error", e);
        } finally {
            this.close(pstm, rs);
        }
        return configList;
    }

    public Config getConfig(String configId) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        Config config = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.* FROM config c WHERE c.conf_id = ? ";
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, configId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                config = new Config();
                config.setConfId(rs.getString("conf_id"));
                config.setConfCode(rs.getString("conf_code"));
                config.setConfName(rs.getString("conf_name"));
                config.setConfValue(rs.getString("conf_value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getConfig error", e);
        } finally {
            this.close(pstm, rs);
        }
        return config;
    }
    
    public Config getConfigUnique(String configName) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        Config config = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.* FROM config c WHERE c.conf_name = ? ";
            logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, configName);
            rs = pstm.executeQuery();
            while (rs.next()) {
                config = new Config();
                config.setConfId(rs.getString("conf_id"));
                config.setConfCode(rs.getString("conf_code"));
                config.setConfName(rs.getString("conf_name"));
                config.setConfValue(rs.getString("conf_value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getConfig error", e);
        } finally {
            this.close(pstm, rs);
        }
        return config;
    }

    public List<Config> getAllConfig(int limit, int offset) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Config> configList = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT c.* FROM config c ORDER BY c.conf_id limit " + limit + " offset " + offset;
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            configList = new ArrayList<Config>();
            while (rs.next()) {
                Config config = new Config();
                config.setConfId(rs.getString("conf_id"));
                config.setConfCode(rs.getString("conf_code"));
                config.setConfName(rs.getString("conf_name"));
                config.setConfValue(rs.getString("conf_value"));
                configList.add(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getAllConfig error", e);
        } finally {
            this.close(pstm, rs);
        }
        return configList;
    }

    public int createConfig(Config config) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" INSERT INTO `config` ");
            sql.append(" ( `conf_code`, `conf_name`, `conf_value` ) ");
            sql.append(" VALUES ");
            sql.append(" (?,?,?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, config.getConfCode());
            pstm.setString(2, config.getConfName());
            pstm.setString(3, config.getConfValue());
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("createConfig error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int updateConfig(Config config) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE `config` SET ");
            sql.append(" `conf_code`=?,`conf_name`=?,`conf_value`=? ");
            sql.append(" WHERE `conf_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, config.getConfCode());
            pstm.setString(2, config.getConfName());
            pstm.setString(3, config.getConfValue());
            pstm.setString(4, config.getConfId());

            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveSim error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    public int deleteConfig(int configId) {
        int exe = 0;
        PreparedStatement pstm = null;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM `config` WHERE conf_id=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setInt(1, configId);
            exe = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteConfig error", e);
        } finally {
            this.close(pstm, null);
        }
        return exe;
    }

    private void close(PreparedStatement pstm, ResultSet rs) {
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
            logger.error("getUser error", ex);
        }
    }

    public int getCountConfig() {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cnt FROM config c");
            pstm = conn.prepareStatement(sql.toString());
            rs = pstm.executeQuery();
            if (rs.next()) {
                countRecord = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getCountConfig error", e);
        } finally {
            this.close(pstm, rs);
        }
        return countRecord;
    }
}

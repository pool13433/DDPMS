package com.ddpms.dao;

import com.ddpms.db.DbConnection;
import com.ddpms.model.Config;
import com.ddpms.util.CharacterUtil;
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
    private final String STR_TO_DATE = " '%d-%m-%Y' ";
    private final String DATE_TO_STR = " '%d-%m-%Y' ";

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
            logger.info("sql ::==" + sql);
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

    public List<Config> getAllConfig(int limit, int offset,String sqlCondition) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        List<Config> configList = null;
        try {
            conn = new DbConnection().open();
            String sql = "SELECT `conf_id`, `conf_code`, `conf_name`, `conf_value`,modified_by ";
            sql += " ,DATE_FORMAT(modified_date," + DATE_TO_STR + ") as modified_date  FROM config c ";
            sql += sqlCondition;
            sql += " ORDER BY c.conf_id limit " + limit + " offset " + offset;
            //logger.info("sql ::=="+sql);
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            configList = new ArrayList<Config>();
            while (rs.next()) {
                configList.add(this.getEntityConfig(rs));
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
            sql.append(" ( `conf_code`, `conf_name`, `conf_value`,modified_date,modified_by ) ");
            sql.append(" VALUES ");
            sql.append(" (?,?,?,NOW(),?)");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, config.getConfCode());
            pstm.setString(2, config.getConfName());
            pstm.setString(3, config.getConfValue());
            pstm.setString(4, config.getModifiedBy());
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
            sql.append(" `conf_code`=?,`conf_name`=?,`conf_value`=?,modified_date=NOW(),modified_by=? ");
            sql.append(" WHERE `conf_id`=?");

            pstm = conn.prepareStatement(sql.toString());
            pstm.setString(1, config.getConfCode());
            pstm.setString(2, config.getConfName());
            pstm.setString(3, config.getConfValue());
            pstm.setString(4, config.getModifiedBy());
            pstm.setString(5, config.getConfId());

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

    public int getCountConfig(String sqlCondition) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int countRecord = 0;
        try {
            conn = new DbConnection().open();
            String sql = " SELECT COUNT(*) as cnt FROM config c "+sqlCondition;
            pstm = conn.prepareStatement(sql);
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

    private Config getEntityConfig(ResultSet rs) throws SQLException {
        Config config = new Config();
        config.setConfCode(rs.getString("conf_code"));
        config.setConfId(rs.getString("conf_id"));
        config.setConfName(rs.getString("conf_name"));
        config.setConfValue(rs.getString("conf_value"));
        config.setModifiedDate(rs.getString("modified_date"));
        config.setModifiedBy(rs.getString("modified_by"));
        return config;
    }

    public String getConditionBuilder(Config condition) {
        String sql = " WHERE 1=1 ";
        if (!CharacterUtil.removeNull(condition.getConfCode()).equals("")) {
            sql += " AND conf_code LIKE '%" + condition.getConfCode() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getConfName()).equals("")) {
            sql += " AND conf_name LIKE '%" + condition.getConfName() + "%' ";
        }
        if (!CharacterUtil.removeNull(condition.getConfValue()).equals("")) {
            sql += " AND conf_value LIKE '%" + condition.getConfValue() + "%' ";
        }
        return sql;
    }
}

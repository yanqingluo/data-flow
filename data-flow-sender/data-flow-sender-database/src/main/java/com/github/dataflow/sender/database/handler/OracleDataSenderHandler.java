package com.github.dataflow.sender.database.handler;

import com.github.dataflow.common.utils.PropertyUtil;
import com.github.dataflow.dubbo.model.DataOutputMapping;
import com.github.dataflow.sender.core.DataSender;
import com.github.dataflow.sender.core.datasource.DataSourceHolder;
import com.github.dataflow.sender.core.exception.DataSenderException;
import com.github.dataflow.sender.database.OracleDataSender;
import com.github.dataflow.sender.database.config.DatabaseConfig;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/6/24
 */
public class OracleDataSenderHandler extends AbstractDatabaseDataSenderHandler {
    @Override
    protected DataSender createDataSender(DataOutputMapping dataOutputMapping) throws Exception {
        OracleDataSender dataSender = new OracleDataSender();
        Properties props = parseToProperties(dataOutputMapping.getDataSourceOutput().getOptions());
        dataSender.setDataSourceHolder(new DataSourceHolder(getOracleDataSource(props)));
        return dataSender;
    }

    private DataSource getOracleDataSource(Properties props) {
        // TODO 优化连接池
        OracleDataSource oracleDataSource = null;
        try {
            oracleDataSource = new OracleDataSource();
            oracleDataSource.setURL(PropertyUtil.getString(props, DatabaseConfig.JDBC_URL));
            oracleDataSource.setUser(PropertyUtil.getString(props, DatabaseConfig.USERNAME));
            oracleDataSource.setPassword(PropertyUtil.getString(props, DatabaseConfig.PASSWORD));
            return oracleDataSource;
        } catch (SQLException e) {
            throw new DataSenderException(e);
        }
    }
}

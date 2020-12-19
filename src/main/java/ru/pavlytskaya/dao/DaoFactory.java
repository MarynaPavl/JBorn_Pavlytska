package ru.pavlytskaya.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoFactory {
    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(getDataSource());
        }
        return userDao;
    }

    private static AccountDao accountDao;

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao(getDataSource());
        }
        return accountDao;
    }

    private static TypeDao typeDao;

    public static TypeDao getTypeDao() {
        if (typeDao == null) {
            typeDao = new TypeDao(getDataSource());
        }
        return typeDao;
    }

    private static TransactionInformationDao transactionInformationDao;

    public static TransactionInformationDao getInformationDao() {
        if (transactionInformationDao == null) {
            transactionInformationDao = new TransactionInformationDao(getDataSource());
        }
        return transactionInformationDao;
    }

    private static TransactionToCategoryDao transactionToCategoryDao;

    public static TransactionToCategoryDao getTransactionToCategoryDao(){
        if (transactionToCategoryDao == null) {
          transactionToCategoryDao = new TransactionToCategoryDao(getDataSource());
        }
        return transactionToCategoryDao;
    }

    public static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(System.getProperty("jdbcUrl","jdbc:postgresql://localhost:5432/postgres"));
            config.setUsername(System.getProperty("jdbcUser","postgres"));
            config.setPassword(System.getProperty("jdbcPassword","postgres"));

            dataSource = new HikariDataSource(config);
            initDatabase();
        }
        return dataSource;
    }

    private static void initDatabase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(
                    System.getProperty("liquibaseFile","liquibase.xml"),
                    new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update(new Contexts());
        }catch (SQLException | LiquibaseException e){
            throw new RuntimeException(e);
        }
    }
}

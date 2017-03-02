package uk.co.kstech.rest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by KMcGivern on 02/03/2017.
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class RepositoryConfiguration {

    private String driverClassName;

    private String username;

    private String password;

    private String url;

    private int connectionPoolInitialSize;

    private int maxTotal;

    private int maxIdle;

    private String validationQuery;

    private int validationQueryTimeout;

    private int defaultQueryTimeout;

    public int getDefaultQueryTimeout() {
        return defaultQueryTimeout;
    }

    public void setDefaultQueryTimeout(int defaultQueryTimeout) {
        this.defaultQueryTimeout = defaultQueryTimeout;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String dbDriverClassName) {
        this.driverClassName = dbDriverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectionPoolInitialSize() {
        return connectionPoolInitialSize;
    }

    public void setConnectionPoolInitialSize(int connectionPoolInitialSize) {
        this.connectionPoolInitialSize = connectionPoolInitialSize;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }
}

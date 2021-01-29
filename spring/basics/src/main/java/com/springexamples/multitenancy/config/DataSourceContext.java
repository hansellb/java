package com.springexamples.multitenancy.config;

import org.springframework.util.Assert;

public class DataSourceContext {
    private static final ThreadLocal<String> tenant = new ThreadLocal<>();

    public static void setCurrentDataSourceTenant(String dataSourceTenant) {
        Assert.notNull(dataSourceTenant, "dataSourceTenant cannot be null");
        tenant.set(dataSourceTenant);
    }
    public static String getCurrentDataSourceTenant() {
        return tenant.get();
    }
    public static void clear() {
        tenant.remove();
    }
}

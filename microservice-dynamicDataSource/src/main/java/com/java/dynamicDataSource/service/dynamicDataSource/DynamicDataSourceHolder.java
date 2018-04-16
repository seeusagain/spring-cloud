package com.java.dynamicDataSource.service.dynamicDataSource;

/**
 * Created by lu.xu on 2017/7/20.
 * TODO:被DynamicDataSource使用;
 * 作用：用于持有当前线程中使用的数据源标识
 * 切换方式：
 * public void test(){
 *   DynamicDataSourceHolder.setDataSource("dataSource");
 *   dataMapper.test();
 *   }
 * 但是建议使用@DataSource自定义注解
 */
public class DynamicDataSourceHolder {
    /**
     * 数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
     */
    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();
    
    public static String getDataSource() {
        return THREAD_DATA_SOURCE.get();
    }
    
    public static void setDataSource(String dataSource) {
        THREAD_DATA_SOURCE.set(dataSource);
    }
    
    public static void clearDataSource() {
        THREAD_DATA_SOURCE.remove();
    }
    
}

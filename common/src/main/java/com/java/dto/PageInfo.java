package com.java.dto;

import java.io.Serializable;

/**
 * Created by lu.xu on 2017/12/26.
 * TODO: 分页查询信息
 */
public class PageInfo implements Serializable {
    /**
     * 查询的页码
     */
    private int page = 1;
    
    /**
     * 每页最大显示条数，如果pageSize参数大于此参数，则返回此参数，详见pageSize的get方法
     */
    private static final int maxPageSize = 20;
    
    /**
     * 每页显示数量
     */
    private int size = 10;
    
    /**
     * 查询结果集总数
     */
    private Object total;
    
    /**
     * 分页结果集
     */
    private Object resultSets;
    
    public int getPage() {
        if (page > 1) {
            return page;
        }
        return 1;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public static int getMaxPageSize() {
        return maxPageSize;
    }
    
    public int getSize() {
        if (size > maxPageSize) {
            return maxPageSize;
        }
        return size;
        
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public Object getTotal() {
        return total;
    }
    
    public void setTotal(Object total) {
        this.total = total;
    }
    
    public Object getResultSets() {
        return resultSets;
    }
    
    public void setResultSets(Object resultSets) {
        this.resultSets = resultSets;
    }
    
    /**
     * 定义数据库类型
     */
    public enum DbTypeEnums {
        DB_TYPE_MYSQL("MYSQL", "MYSQL"), DB_TYPE_ORACLE("ORACLE", "ORACLE");
        private String code;
        
        private String codeName;
        
        DbTypeEnums(String code, String codeName) {
            this.code = code;
            this.codeName = codeName;
        }
        
        DbTypeEnums() {
        }
        
        public String getCode() {
            return code;
        }
        
        public String getCodeName() {
            return codeName;
        }
    }
    
    /**
     * 获取开始记录数
     */
    public int getStartRecord() {
        return this.getStartRecord(DbTypeEnums.DB_TYPE_MYSQL.getCode());
    }
    
    public int getStartRecord(String dbType) {
        int startRecode = 0;
        if (this.getPage() < 1) {
            return startRecode;
        }
        if (DbTypeEnums.DB_TYPE_MYSQL.getCode().equals(dbType)) {
            return (this.getPage() - 1) * size;
        } else if (DbTypeEnums.DB_TYPE_ORACLE.getCode().equals(dbType)) {
            return (this.getPage() - 1) * size;
        }
        return startRecode;
    }
    
    /**
     * 获取结束记录数
     * @return
     */
    public int getEndRecord() {
        return this.getEndRecord(DbTypeEnums.DB_TYPE_MYSQL.getCode());
    }
    
    public int getEndRecord(String dbType) {
        return this.getStartRecord(dbType) + this.getSize();
    }
    
}

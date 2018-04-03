package com.java.exampleSharding.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "switch_test")
public class SwitchTest {
    @Id
    private long testId;
    
    private String name;
    
    public long getTestId() {
        return testId;
    }
    
    public void setTestId(long testId) {
        this.testId = testId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
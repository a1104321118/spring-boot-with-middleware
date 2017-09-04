package com.hr.model;

import java.util.Date;

/**
 * Created by hr on 2017/08/21.
 */
public class IdTest {

    private long id;
    private String systemName;
    private long currentNo;
    private long stepSize;
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public long getCurrentNo() {
        return currentNo;
    }

    public void setCurrentNo(long currentNo) {
        this.currentNo = currentNo;
    }

    public long getStepSize() {
        return stepSize;
    }

    public void setStepSize(long stepSize) {
        this.stepSize = stepSize;
    }
}

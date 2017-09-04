package com.hr.cache;


import java.util.Date;

/**
 * Created by JH.li on 16/11/21.
 */
public class CachePojo<T> {
    private T result;
    private Date createTime;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CachePojo{");
        sb.append("result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}

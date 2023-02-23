package com.interview.natwest;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private final Long snapshot;
    private final List<SecurityData> securityDataList;

    public Data(Long snapshot, List<SecurityData> securityDataList){
        this.snapshot = snapshot;
        this.securityDataList = securityDataList;
    }

    public Long getSnapshot() {
        return snapshot;
    }

    public List<SecurityData> getSecurityDataList() {
        return securityDataList;
    }

    @Override
    public String toString() {
        return "Data{" +
                "snapshot=" + snapshot +
                ", securityDataList=" + securityDataList +
                '}';
    }
}

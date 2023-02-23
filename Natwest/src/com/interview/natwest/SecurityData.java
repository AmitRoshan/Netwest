package com.interview.natwest;

public class SecurityData {

    private String securityName;
    private int buyValue;
    private int sellValue;

    public SecurityData(String securityName, int buyValue, int sellValue){
        this.securityName = securityName;
        this.buyValue = buyValue;
        this.sellValue = sellValue;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public int getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(int buyValue) {
        this.buyValue = buyValue;
    }

    public int getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    @Override
    public String toString() {
        return "SecurityData{" +
                "securityName='" + securityName + '\'' +
                ", buyValue=" + buyValue +
                ", sellValue=" + sellValue +
                '}';
    }
}

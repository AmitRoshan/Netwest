package com.interview.natwest.test;


import com.interview.natwest.Data;
import com.interview.natwest.SecurityData;
import com.interview.natwest.SecurityRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

class SecurityRepoTest {

    private SecurityRepo securityRepo = SecurityRepo.getInstance();
    private Random random = new Random();

    private NavigableMap <Long,ConcurrentHashMap<String, SecurityData>> checkLatestData(){
        try {
            Field field = SecurityRepo.class.getDeclaredField("latestData");
            field.setAccessible(true);
            return (NavigableMap <Long,ConcurrentHashMap<String, SecurityData>>) field.get(securityRepo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private List<SecurityData> getSecurityData() {

        List<SecurityData> securityData = new ArrayList<>();
        for(int i = 0; i< 5; i++){
            securityData.add(new SecurityData(getSecurityName(), getPrice(), getPrice()));
        }
        return securityData;
    }

    private int getPrice() {
        return random.nextInt(100);
    }

    private String getSecurityName() {
        String [] securityName = {"A", "B","C", "D", "E"};
        return securityName[random.nextInt(5)];
    }

    @Test
    void saveData() {
        NavigableMap <Long,ConcurrentHashMap<String, SecurityData>> latestData = checkLatestData();
        Data data = new Data(System.currentTimeMillis(), getSecurityData());

        Assert.assertNotNull(latestData);
        Assert.assertEquals(latestData.size(), 0);

        securityRepo.saveData(data);

        Assert.assertNotNull(latestData);
        Assert.assertEquals(latestData.size(), 1);

    }

    @Test
    void findBySnapshotAndSecurityId() {
        List<SecurityData> securityData = List.of(new SecurityData("A", 55, 70),
                new SecurityData("B", 58, 90));
        Data data = new Data(1L, securityData);

        securityRepo.saveData(data);
        int buy = securityRepo.findBySnapshotAndSecurityId(1L, "A", SecurityRepo.PriceType.BUY);
        Assert.assertEquals(buy, 55);
    }

}
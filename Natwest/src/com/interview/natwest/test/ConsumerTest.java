package com.interview.natwest.test;

import com.interview.natwest.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest {

    private Random random = new Random();
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
    void run() throws InterruptedException {

        Topic topic = Topic.getInstance();
        SecurityRepo securityRepo = SecurityRepo.getInstance();

        Consumer consumer = new Consumer(topic,securityRepo);
        topic.put(new Data(System.currentTimeMillis(), getSecurityData()));
        topic.put(new Data(System.currentTimeMillis(), getSecurityData()));

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(consumer);
        executor.awaitTermination(2, TimeUnit.SECONDS);

        NavigableMap<Long, ConcurrentHashMap<String, SecurityData>> persistData = null;
        try {
            Field field = SecurityRepo.class.getDeclaredField("latestData");
            field.setAccessible(true);
            persistData =  (NavigableMap <Long,ConcurrentHashMap<String, SecurityData>>) field.get(securityRepo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(persistData);
        Assert.assertEquals(persistData.size(), 2);

    }
}
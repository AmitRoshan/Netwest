package com.interview.natwest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {

    private final Topic topic;
    private Random random = new Random();

    public Producer(Topic topic){
        this.topic = topic;
    }

    @Override
    public void run() {
        //int i = 0;
        while (true){
            try {
                Thread.sleep(100);
                topic.put(new Data(System.currentTimeMillis(), getSecurityData()));
                //System.out.println("pro = "+ ++i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
}

package com.interview.natwest;

import java.util.Random;

public class Client implements Runnable {

    SecurityRepo securityRepo;

    public Client(SecurityRepo securityRepo) {
        this.securityRepo = securityRepo;
    }


    @Override
    public void run() {
        while (true) {
            System.out.println("get data");
            SecurityRepo.PriceType priceType;
            Long snapshot = System.currentTimeMillis();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            if (random.nextBoolean())
                priceType = SecurityRepo.PriceType.BUY;
            else
                priceType = SecurityRepo.PriceType.SELL;

            System.out.println("priceType = "+ priceType +", "+ + securityRepo.findBySnapshotAndSecurityId(snapshot, "A", priceType));
        }
    }
}

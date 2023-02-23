package com.interview.natwest;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecurityRepo {

    public enum PriceType {
        BUY,
        SELL
    }

    private SecurityRepo() {
    }

    private static class SecurityRepoHolder {
        private static final SecurityRepo securityRepo = new SecurityRepo();
    }

    public static SecurityRepo getInstance() {
        return SecurityRepoHolder.securityRepo;
    }

    private final NavigableMap<Long, ConcurrentHashMap<String, SecurityData>> latestData = new TreeMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void saveData(Data data) {
        //System.out.println(data);
        //long start = System.nanoTime();

        //ConcurrentHashMap<String, SecurityData> securityDataMap = latestData.get(data.getSnapshot());

        /*if (securityDataMap == null) {
            securityDataMap = new ConcurrentHashMap<>();
            latestData.put(data.getSnapshot(), securityDataMap);
        }*/

        latestData.putIfAbsent(data.getSnapshot(), new ConcurrentHashMap<>());

        for (SecurityData securityData : data.getSecurityDataList()) {
            executorService.submit(saveOrUpdateSecurity(latestData.get(data.getSnapshot()), securityData));
        }

        /*System.out.println(System.nanoTime() - start);
        System.out.println("latestedDate size = " + latestData.size());
        System.out.println("map  = " + latestData.toString());
        System.out.println();*/
    }

    public int findBySnapshotAndSecurityId(Long snapshot, String securityId, Enum priceType) {

        int price = -1;

        Map.Entry<Long, ConcurrentHashMap<String, SecurityData>> longConcurrentHashMapEntry = latestData.ceilingEntry(snapshot);
        if (longConcurrentHashMapEntry != null) {
            ConcurrentHashMap<String, SecurityData> value = longConcurrentHashMapEntry.getValue();
            if (value != null) {
                SecurityData securityData = value.get(securityId);
                if (securityData != null) {
                    if (priceType == PriceType.BUY)
                        price = securityData.getBuyValue();

                    if (priceType == PriceType.SELL)
                        price = securityData.getSellValue();
                }
            }
        }
        return price;

    }

    private Runnable saveOrUpdateSecurity(ConcurrentHashMap<String, SecurityData> securityDataMap, SecurityData securityData) {

        return () -> {
            SecurityData returnedSecurityData = securityDataMap
                    .putIfAbsent(securityData.getSecurityName(), securityData);

            if (returnedSecurityData != null) {
                if (returnedSecurityData.getBuyValue() > securityData.getBuyValue())
                    returnedSecurityData.setBuyValue(securityData.getBuyValue());

                if (returnedSecurityData.getSellValue() < securityData.getSellValue())
                    returnedSecurityData.setSellValue(securityData.getSellValue());
            }


        };
    }


}

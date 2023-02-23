package com.interview.natwest.test;

import com.interview.natwest.Data;
import com.interview.natwest.Producer;
import com.interview.natwest.SecurityRepo;
import com.interview.natwest.Topic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.NavigableMap;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {

    @Test
    void run() throws InterruptedException {

        Topic topic = Topic.getInstance();
        SecurityRepo securityRepo = SecurityRepo.getInstance();

        Producer producer = new Producer(topic);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(producer);
        executor.awaitTermination(2, TimeUnit.SECONDS);


        BlockingQueue<Data> blockingQueue = null;
        try {
            Field field = Topic.class.getDeclaredField("blockingQueue");
            field.setAccessible(true);
            blockingQueue =  (BlockingQueue<Data>) field.get(topic);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(blockingQueue);
        Assert.assertTrue("blockingQueue is not empty", blockingQueue.size() > 0);

    }
}
package com.interview.natwest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private Topic(){}

    private static class TopicHolder{
        private static final Topic topic = new Topic();
    }

    public static Topic getInstance(){
        return TopicHolder.topic;
    }

    private final BlockingQueue<Data> blockingQueue = new LinkedBlockingQueue<>();

    public void put(Data data) throws InterruptedException {
        blockingQueue.put(data);
    }


    public Data take() throws InterruptedException {
        return blockingQueue.take();
    }
}

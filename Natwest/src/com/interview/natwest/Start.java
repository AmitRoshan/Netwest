package com.interview.natwest;

public class Start {

    public static void main(String[] args) {
        Topic topic = Topic.getInstance();
        SecurityRepo securityRepo = SecurityRepo.getInstance();

        Producer producer = new Producer(topic);
        Consumer consumer = new Consumer(topic,securityRepo);

        Thread pThread = new Thread(producer);
        Thread cThread = new Thread(consumer);

        pThread.start();
        cThread.start();

        Client client = new Client(securityRepo);
        new Thread(client).start();
    }
}

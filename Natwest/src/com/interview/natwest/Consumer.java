package com.interview.natwest;

public class Consumer implements Runnable {

    private final Topic topic;
    private final SecurityRepo securityRepo;

    public Consumer(Topic topic, SecurityRepo securityRepo) {
        this.topic = topic;
        this.securityRepo = securityRepo;
    }

    @Override
    public void run() {
        while (true){
            try {
                processData(topic.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processData(Data data) {
        securityRepo.saveData(data);
    }
}

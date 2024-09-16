package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class URLQueue {
    private BlockingQueue<String> urlQueue;

    public URLQueue() {
        this.urlQueue = new LinkedBlockingDeque<>();
    }

    public void addUrl(String url){
        try {
            urlQueue.put(url);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNextUrl(){
        try {
            return urlQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty(){
        return urlQueue.isEmpty();
    }
}

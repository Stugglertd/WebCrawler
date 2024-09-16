package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CrawledDataStore {
    private Map<String,String> crawledPages;

    public CrawledDataStore(){
        crawledPages = new ConcurrentHashMap<>();
    }

    public void store(String url,String content){
        crawledPages.put(url,content);
    }

    public boolean hasUrlBeenCrawled(String url){
        return crawledPages.containsKey(url);
    }

    public String getContent(String url){
        return crawledPages.get(url);
    }
}

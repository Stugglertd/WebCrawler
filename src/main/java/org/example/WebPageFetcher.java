package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebPageFetcher implements Runnable{
    private final URLQueue urlQueue;
    private final CrawledDataStore dataStore;

    public WebPageFetcher(URLQueue urlQueue, CrawledDataStore dataStore) {
        this.urlQueue = urlQueue;
        this.dataStore = dataStore;
    }

    @Override
    public void run() {
        while(!urlQueue.isEmpty()){
            final String url = urlQueue.getNextUrl();
            if(url == null || dataStore.hasUrlBeenCrawled(url))
                continue;

            try {
                final Document document = Jsoup.connect(url).get();
                final String content = document.body().text();

                Main.extractUrls(document,urlQueue,dataStore);

                dataStore.store(url,content);
                Thread.sleep(4000);

                System.out.println(Thread.currentThread().getName() + " - crawled: " + url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

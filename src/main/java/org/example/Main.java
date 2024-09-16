package org.example;

import org.example.utils.CustomRejectHandler;
import org.example.utils.CustomThreadFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.*;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        URLQueue urlQueue = new URLQueue();
        CrawledDataStore crawledDataStore = new CrawledDataStore();

        WebPageFetcher webPageFetcher = new WebPageFetcher(urlQueue,crawledDataStore);

        urlQueue.addUrl("https://www.youtube.com/");
        urlQueue.addUrl("http://www.javatpoint.com");

        int corePoolSize = 3;
        int maxPoolSize = 5;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;

        ThreadPoolExecutor executorService =
                new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveTime,unit, new ArrayBlockingQueue<>(5),new CustomThreadFactory(),new CustomRejectHandler());
        for(int i=0;i<5;i++) {
            executorService.submit(webPageFetcher);
        }

        executorService.shutdown();
    }
    public static void extractUrls(Document doc, URLQueue queue, CrawledDataStore store) {
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String url = link.attr("abs:href");
            if (!store.hasUrlBeenCrawled(url)) {
                queue.addUrl(url);
            }
        }
    }
}
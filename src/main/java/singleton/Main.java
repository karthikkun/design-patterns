package singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
//        TVSet tvset = TVSet.getInstance();
//        TVSet tvset2 = TVSet.getInstance();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> TVSet.getInstance());
        executorService.execute(() -> TVSet.getInstance());
//        System.out.println(tvset.hashCode());
//        System.out.println(tvset2.hashCode());
    }
}


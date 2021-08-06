package practice;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Runnable myRunnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "  finished");
            } catch (Exception e) {
                System.out.printf("error happen %s", e.getMessage());
            }
        };

        Thread t1 = new Thread(myRunnable);

        t1.start();
        t1.join();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(myRunnable);

        for (int i = 0; i < 10; i++) {
            executor.submit(myRunnable);
        }


        // with return value
        Future<Integer> f1 = executor.submit(() -> { return 42; });
        System.out.println(f1.get());

        executor.shutdown();

        System.out.println("Play with completable future");

        long start = System.currentTimeMillis();
        Supplier<Integer> sup1 = () -> {
            try {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(2000);
                return 12;
            } catch (Exception e) {
                return 0;
            }
        };
        CompletableFuture<Integer> c1 = CompletableFuture.supplyAsync(sup1);
        CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(sup1);

        System.out.println(c1.get() + c2.get());
        long end = System.currentTimeMillis();
        System.out.printf("Running time: %d seconds", (end - start)/ 1000);
    }
}

package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Supplier<Integer> supplier = () -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(5 * 1000); //5s
                return 42;
            } catch (Exception e) {
                return 11;
            }
        };
        CompletableFuture<Integer> fInt1 = CompletableFuture.supplyAsync(supplier);

        // register
        fInt1.thenRun(() -> {
            try {
                System.out.printf("%s Got value: %d \n", Thread.currentThread().getName(), fInt1.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // force Main thread wait 6s to see the above register function process
        // can think about it as heavy computational job
        Thread.sleep(6 * 1000);
    }
}

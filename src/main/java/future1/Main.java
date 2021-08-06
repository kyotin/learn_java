package future1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();

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

        CompletableFuture<Integer> fInt2 = CompletableFuture.supplyAsync(supplier);

        Function<Integer, Integer> apl = (v) -> {
            System.out.printf("%s Applying .. \n", Thread.currentThread().getName());
            return v + 1;
        };

        // wait here to get value
        System.out.println(fInt1.thenApply(apl).thenCombine(fInt2, Integer::sum).get());

        long endTime = System.currentTimeMillis();
        System.out.printf("Total time: %d \n", endTime - startTime);
    }
}
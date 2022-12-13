import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Assignment8 assignment8 = new Assignment8();
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Integer> numbersList = Collections.synchronizedList(new ArrayList<>(1000));
        List<CompletableFuture<Void>> tasks = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment8.getNumbers(), pool)
                    .thenAccept(numbers -> numbersList.addAll(numbers));
            tasks.add(task);
        }

        while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }

        System.out.println(numbersList.size());
    }
}

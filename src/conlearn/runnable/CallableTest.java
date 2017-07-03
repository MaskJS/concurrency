package conlearn.runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 */
public class CallableTest {
    static class DeliverCallable implements Callable<String> {
        public String call() throws Exception {
            Thread.sleep(new Random().nextInt(10000));
            System.out.println(Thread.currentThread().getName()+": done");
            return Thread.currentThread().getName()+"finishTime :" + System.currentTimeMillis() + "\n";
        }
    }

    public static void main(String args[]) {
        List<FutureTask<String>> futureTasks = new ArrayList<>(4);
        for (int i = 0; i < 4; i++){
            DeliverCallable callable = new DeliverCallable();
            FutureTask<String> futureTask = new FutureTask<String>(callable);
            futureTasks.add(futureTask);

            Thread thread = new Thread(futureTask, "job" +  i);
            thread.start();
        }

        StringBuilder results = new StringBuilder();
        futureTasks.forEach(futureTask -> {
            try {
                results.append(futureTask.get());
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        });

        System.out.println(System.currentTimeMillis() + " get result: \n" + results);
    }
}

package conlearn.runnable;

import java.util.Random;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 */
public class ThreadTestRunnable {

    static class Task implements Runnable{
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(300));
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ": done");
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < 5; i++) {

            /*
            * 子线程执行
            * */
            Thread job = new Thread(new Task(),"job"+i);
            job.start();

            /*
            * 主线程执行
            * */
//            Task task = new Task();
//            task.run();
        }
    }
}

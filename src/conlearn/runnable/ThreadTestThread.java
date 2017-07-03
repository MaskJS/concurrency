package conlearn.runnable;

import java.util.Random;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 */
public class ThreadTestThread {
    static class DeliverThread extends Thread{
        private DeliverThread(String name) {

            super(name);//调用父类Thread的构造函数

        }

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
            DeliverThread job = new DeliverThread("job " + i);
            job.start();
        }
    }
}

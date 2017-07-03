package conlearn.runnable;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 * 测试Runnable和Thread进程不响应中断信号
 */
public class ThreadInterruptBySignal {

    static class UnInterruptByThread extends Thread{

        public UnInterruptByThread(String s) {
            setName(s);
        }

        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }
    }

    static class UnInterruptByRunnable implements Runnable{

        public void run() {
           while (true) {
               System.out.println(Thread.currentThread().getName() + " is running");
           }
        }
    }

    public static void main(String args[]) throws InterruptedException {
//        UnInterruptByThread thread = new UnInterruptByThread("byThread");
        Thread thread = new Thread(new UnInterruptByRunnable(),"byRunnable");
        thread.start();

        Thread.sleep(1000);
        thread.interrupt();

        Thread.sleep(1000);

    }
}

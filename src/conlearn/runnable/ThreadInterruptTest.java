package conlearn.runnable;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 * 线程中断练习
 */
public class ThreadInterruptTest {
    static class SleepThread extends Thread{

        SleepThread(String s) {
            setName(s);
        }

        public void run() {
            while (!isInterrupted()) {
                try {
                    sleep(1500);
                    System.out.println(Thread.currentThread().getName() + "running: " + System.currentTimeMillis());
                }catch (InterruptedException e) {
                    e.printStackTrace();//抛出异常后，会导致无法正确中断
                    System.out.println(Thread.currentThread().getName() + "is interrupted and the flag of interrupt is " + isInterrupted());
                    destroy();//调用distory方法直接杀死
                }
            }
        }
    }

    static class BusyThread extends Thread{

        BusyThread(String s) {
            setName(s);
        }

        public void run() {
            while (!isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + "running: " + System.currentTimeMillis());
            }
        }
    }

    public static void main(String args[]) {
        SleepThread sleepThread = new SleepThread("sleepThread");
        BusyThread busyThread = new BusyThread("busyThread");

        System.out.println("Sleep Thread start with flag: " + sleepThread.isInterrupted());
        System.out.println("Busy Thread start with flag: " + busyThread.isInterrupted());

        threadSleep(2000);

        sleepThread.start();
        busyThread.start();

        threadSleep(1000);

        sleepThread.interrupt();
        busyThread.interrupt();

        threadSleep(2000);

        System.out.println("sleep thread stop with flag " + sleepThread.isInterrupted());
        System.out.println("busy thread stop with flag " + busyThread.isInterrupted());

    }

    private static void threadSleep(int time) {
        try {
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}

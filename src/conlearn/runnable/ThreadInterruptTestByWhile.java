package conlearn.runnable;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 * 基于循环实现线程中断
 */
public class ThreadInterruptTestByWhile {
    static class InterruptThread extends Thread{

        private boolean running;

        InterruptThread(boolean running, String name) {
            super(name);
            this.running = running;
        }

        public boolean isRunning() {
            return this.running;
        }

        void setRunning(boolean running) {
            this.running = running;
        }

        public void run() {
            while (this.running) {
                System.out.println("thread " + Thread.currentThread().getName() + " is running" );
            }

            System.out.println("thread " + Thread.currentThread().getName() + " is stoping");
        }
    }

    public static void main(String args[]) {
        InterruptThread thread = new InterruptThread(true, "interrputTest");

        thread.start();

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        thread.setRunning(false);
    }
}

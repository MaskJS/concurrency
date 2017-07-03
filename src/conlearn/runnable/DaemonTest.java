package conlearn.runnable;

/**
 * Created by luoxinyu-xy on 2017/7/3.
 */
public class DaemonTest {
    static class DaemonThread extends Thread{

        private DaemonThread(String name) {
            super(name);
        }

        public void run() {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName() + "finally has been called !");
            }
        }
    }

    public static void main(String args[]) {
        DaemonThread demaon = new DaemonThread("Deamon Test");
        demaon.setDaemon(true);//设置为daemon进程

        demaon.start();//测试finally块不会被执行
    }
}

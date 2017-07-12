package threadpool;

import javafx.concurrent.Worker;
import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by luoxinyu-xy on 2017/7/12.
 * 线程池实现
 */
public class TiThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    private static final int MAX_WORKER_SIZE = 10;

    private static final int DEFAULT_WORKER_SIZE = 5;

    private static final int MIN_WORKER_SIZE = 1;

    private final LinkedList<Job> jobs = new LinkedList<Job>();

    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    private int WorkerNum;

    private AtomicLong threadNum = new AtomicLong();

    public TiThreadPool(int num) {
        if (num > MAX_WORKER_SIZE){
            this.WorkerNum = DEFAULT_WORKER_SIZE;
        }else{
            this.WorkerNum = num;
        }

        initializeWorkers(this.WorkerNum);
    }

    public TiThreadPool() {
        this.WorkerNum = DEFAULT_WORKER_SIZE;

        initializeWorkers(this.WorkerNum);
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            this.workers.add(worker);
            Thread thread = new Thread(worker);

            thread.start();
        }
    }

    public void execute(Job job) {
        if(job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    public void shutdown() {
        for (Worker w : workers){
            w.shutdown();
        }
    }

    public void addWorkers(int num) {
        synchronized (jobs) {
            if(num + this.WorkerNum > MAX_WORKER_SIZE){
                num = MAX_WORKER_SIZE - this.WorkerNum;
            }

            initializeWorkers(num);
            this.WorkerNum += num;
        }
    }

    public void removeWorker (int num) {
        synchronized (jobs) {
            if (num > this.WorkerNum) {
                throw new IllegalArgumentException("remove threads more than running threads!");
            }

            for (int i = 0; i<num; i++) {
                Worker worker = workers.get(i);
                if(worker != null) {
                    worker.shutdown();
                    workers.remove(i);
                }
            }

            this.WorkerNum -= num;
        }
    }

    public int getJobSize() {
        return workers.size();
    }

    public class Worker implements Runnable{

        private volatile boolean running = true;

        public void run() {
            while (running) {
                Job job = null;

                synchronized (jobs) {
                    if (jobs.isEmpty()) {
                        try{
                            jobs.wait();
                        }catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    job = jobs.removeFirst();
                }

                if(job != null){
                    job.run();
                }
            }
        }

        public void shutdown() {
            running = false;
        }

    }
}

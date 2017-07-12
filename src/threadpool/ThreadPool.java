package threadpool;

/**
 * Created by luoxinyu-xy on 2017/7/12.
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    void addWorkers(int nums);

    void removeWorker(int num);

    int getJobSize();
}

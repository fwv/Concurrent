package ExecutorService;

import tools.LogUtils;

import java.util.concurrent.*;

/**
 * @Author fengwei
 * Created on 2016/9/18/0018.
 */
public class CancelTaskInES {

    public static ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(1);

    public static Future future;

    public static void cancelTask() {
        future.cancel(true);
    }

    public static Runnable cancelTaskByCount(final int count) {
        Runnable r = new Runnable() {
            volatile int i = 0;
            public void run() {
                LogUtils.log.info("running : i = " + ++i);
                // 次数限制判定是否取消任务
                if (i > count) {
                    CancelTaskInES.future.cancel(true);
                }
            }
        };
        return r;
    }

    public static void run(Runnable runnable) {
       future =  scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public static void callableRun(Callable c) {
        FutureTask ft = new FutureTask(c);
        Future f =  scheduledExecutorService.scheduleAtFixedRate(ft, 0, 1, TimeUnit.SECONDS);
    }


    public static void main(String[] args) {
        // 获取带有次数限制的runnable任务
        Runnable r = CancelTaskInES.cancelTaskByCount(5);
        // 开始执行
        CancelTaskInES.run(r);
        // 检测任务生命周期中是否抛出异常（这里是“取消异常”）
        try {
            CancelTaskInES.future.get();
        } catch (Exception e) {
           LogUtils.log.info("task is interrupt !!!");
        }
        // 等待3s，开启新的任务检测线程池的活跃性（可用性）
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable r1 = new Runnable() {
           public void run() {
                LogUtils.log.info("new task is running");
            }
        };
        CancelTaskInES.run(r1);

    }

}

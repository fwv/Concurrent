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
                // ?????????ж???????????
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
        // ??????д????????runnable????
        Runnable r = CancelTaskInES.cancelTaskByCount(5);
        // ??????
        CancelTaskInES.run(r);
        // ???????????????????????????????????????????
        try {
            CancelTaskInES.future.get();
        } catch (Exception e) {
           LogUtils.log.info("task is interrupt !!!");
        }
        // ???3s????????????????????????????????
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

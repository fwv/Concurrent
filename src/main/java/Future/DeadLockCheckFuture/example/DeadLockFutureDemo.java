package Future.DeadLockCheckFuture.example;

import Future.DeadLockCheckFuture.DeadLockFuture;
import Future.DeadLockCheckFuture.Future;
import Future.DeadLockCheckFuture.FutureListener;
import tools.LogUtils;


/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public class DeadLockFutureDemo {

    final static Runnable runnable = () -> {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.log.info("task is over!");
    };

    public static void main(String[] args) {
        //DeadLockFutureDemo.generalSituation();
        DeadLockFutureDemo.deadlockSituation();
    }

    private static void generalSituation() {
        DeadLockFuture future = new DeadLockFuture(true);
        future.addListener(new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                LogUtils.log.info("oparation complete! ");
            }

            @Override
            public void exceptionCatch() {

            }
        });
        future.executor(DeadLockFutureDemo.runnable);
        LogUtils.log.info("do other things");
    }

    private static void deadlockSituation() {
        DeadLockFuture future = new DeadLockFuture(true);
        future.addListener(new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                //LogUtils.log.info("oparation complete : " + Thread.currentThread().getName());
                //future.await();
                LogUtils.log.info("oparation complete! ");
            }

            @Override
            public void exceptionCatch() {

            }
        });

        future.executor(DeadLockFutureDemo.runnable);

        try {
            future.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.log.info("do other things");
    }

}

package CountDownLatch;

import tools.LogUtils;

import java.util.concurrent.CountDownLatch;

/**
 * @Author fengwei
 * Created on 2016/9/27/0027.
 */
public class CountdownLatch {

    public static CountDownLatch latch = new CountDownLatch(1);

    public void sayHello() throws InterruptedException {
        LogUtils.log.info("start");
        latch.await();
        LogUtils.log.info("hello");
    }

    public void goOn() {
        latch.countDown();
    }

    public void sayHi() throws InterruptedException {
        latch.await();
        LogUtils.log.info("hi");
    }

    public static void main(String[] args) throws InterruptedException {
        CountdownLatch c = new CountdownLatch();
        new Thread(()->{
            try {
                //Thread.sleep(10000);
                c.sayHello();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        //Thread.sleep(1000);
        c.goOn();
        c.goOn();
        c.sayHi();
    }
}

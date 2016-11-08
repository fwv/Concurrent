package tools;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 * 格式化线程工厂
 */
public class NamedThreadFactory implements ThreadFactory{

    private String ThreadName;

    private AtomicInteger poolNo = new AtomicInteger(0);

    private AtomicInteger threadNo = new AtomicInteger(0);

    public NamedThreadFactory(String threadName) {
        this.ThreadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, ThreadName + "-" + poolNo.getAndIncrement() + "-" + threadNo.getAndIncrement());
    }
}

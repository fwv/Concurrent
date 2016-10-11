package MultiConsumerExecutor;

import sun.java2d.loops.TransformHelper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 * ��ִ����
 */
public class ConsumerExecutor {

    public AbstractMultiConsumerExecutor parentExecutor;

    public ThreadPoolExecutor executor;

    public BlockingQueue queue;

    public ConsumerExecutor() {

        queue = new LinkedBlockingQueue();

        executor = new ThreadPoolExecutor(5, 10, 3, TimeUnit.MINUTES,
                new LinkedBlockingQueue(), new ConsumerThreadFactory(),
                new ConsumerTaskRejectHandler(parentExecutor.messageQueue));

    }

    public ConsumerExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
        queue = new LinkedBlockingQueue();
    }


}

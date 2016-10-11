package MultiConsumerExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 * 多消费模型执行器（二级阻塞队列缓存消息）
 */
public class MultiConsumerExecutor extends AbstractMultiConsumerExecutor{

    public MultiConsumerExecutor() {
        messageQueue = new LinkedBlockingQueue();
        initSubQueue(5);
    }

    public MultiConsumerExecutor(Integer queueSize, Integer subQueuesSize) {
        messageQueue = new ArrayBlockingQueue(queueSize);
        initSubQueue(subQueuesSize);
    }

    public void initSubQueue(int cnt) {
        for (int i = 0; i < cnt; i++) {
            subQueues.add(new LinkedBlockingQueue());
        }
    }

    @Override
    public Future executor(Runnable runnable) {
        try {
            messageQueue.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

}

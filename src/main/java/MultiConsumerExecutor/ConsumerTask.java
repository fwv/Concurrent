package MultiConsumerExecutor;

import java.util.concurrent.Future;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 */
public class ConsumerTask implements Runnable{

    public ConsumerExecutor consumerExecutor;

    public static ConsumerTask create(ConsumerExecutor e) {
        ConsumerTask task = new ConsumerTask();
        task.consumerExecutor = e;
        return task;
    }

    private ConsumerTask(){}

    @Override
    public void run() {
        Runnable message = null;
        for(;;) {
            try {
                message = (Runnable) consumerExecutor.queue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // ����future���Ժ���
            Future future = consumerExecutor.executor.submit(message);
        }
    }
}

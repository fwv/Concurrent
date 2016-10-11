package MultiConsumerExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 * ������ģ��ִ�����������������л�����Ϣ��
 */
public class MultiConsumerExecutor extends AbstractMultiConsumerExecutor{

    public MultiConsumerExecutor() {
        messageQueue = new LinkedBlockingQueue();
        // ��ʼ����ִ����
        initSubQueue(5);
        // �����ַ��߳�
        dispatch();
    }

    public MultiConsumerExecutor(Integer queueSize, Integer subQueuesSize) {
        messageQueue = new ArrayBlockingQueue(queueSize);
        // ��ʼ����ִ����, ����������ִ��������
        initSubQueue(subQueuesSize);
        // �����ַ��߳�
        dispatch();
    }

    public void initSubQueue(int cnt) {
        for (int i = 0; i < cnt; i++) {
            registerConsumer();
        }
    }

    public void dispatch() {
        new Thread(DispatchConsumerTask.create(this), "multi-consumer-dispatch-thread").start();
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

    @Override
    public void registerConsumer() {
        ConsumerExecutor subExecutor = new ConsumerExecutor();
        subExecutor.parentExecutor = this;
        subQueues.add(subExecutor);
    }

    @Override
    public void registerConsumer(ConsumerExecutor subExecutor) {
        subExecutor.parentExecutor = this;
        subQueues.add(subExecutor);
    }

    @Override
    public void unstallConsumer(int index) {
        subQueues.remove(index);
    }

}

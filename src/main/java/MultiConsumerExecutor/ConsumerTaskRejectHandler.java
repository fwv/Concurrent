package MultiConsumerExecutor;

import tools.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 * ��ִ��������ʧ�ܲ��ԣ���ʧ����������һ�����������л���
 */
public class ConsumerTaskRejectHandler implements RejectedExecutionHandler{

    public BlockingQueue messageQueue;

    public ConsumerTaskRejectHandler(BlockingQueue queue) {
        messageQueue = queue;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (null != messageQueue)
        try {
            messageQueue.put(r);
            LogUtils.log.info("Consumer rejected! put message into messageQueue");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

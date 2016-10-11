package MultiConsumerExecutor;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 * ��Ϣ�ַ��̣߳���������������Ϣ�ַ����Ӷ�����
 */
public class DispatchConsumerTask implements Runnable{

    public AbstractMultiConsumerExecutor executor;

    public static DispatchConsumerTask create(AbstractMultiConsumerExecutor executor) {
        DispatchConsumerTask task = new DispatchConsumerTask();
        task.executor = executor;
        return task;
    }

    @Override
    public void run() {
        for(;;) {
            Runnable message = null;
            if (null != executor.messageQueue)
            try {
                message = (Runnable) executor.messageQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (null != executor.subQueues && 0 != executor.subQueues.size()) {
                int index = (int) (System.nanoTime() % executor.subQueues.size());
                try {
                    // Ŀǰʹ�ü򵥹�ϣ���պ��ѡ��һ����hash
                    executor.subQueues.get(index).put(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

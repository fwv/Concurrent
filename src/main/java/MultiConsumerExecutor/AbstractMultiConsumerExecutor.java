package MultiConsumerExecutor;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

/**
 * @Author fengwei
 * Created on 2016/10/11/0011.
 */
public abstract class AbstractMultiConsumerExecutor {

    protected BlockingQueue messageQueue;

    protected ArrayList<BlockingQueue> subQueues;

    protected Integer SUB_QUEUE_COUNT;

    public abstract Future executor(Runnable message);

}

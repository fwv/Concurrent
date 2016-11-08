package Future.DeadLockCheckFuture;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public interface FutureListener {

    void operationComplete(Future future) throws Exception;

    void exceptionCatch();

}

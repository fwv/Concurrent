package Future.DeadLockCheckFuture;


/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public interface Future {

    boolean isDone();

    boolean isSucess();

    boolean setSuccess();

    Throwable getException();

    void addListener(FutureListener listener);

    Future await() throws InterruptedException;

    Future awaitUninterruptly();

    Future notifyListener();

}

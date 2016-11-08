package Future.DeadLockCheckFuture;

import java.util.concurrent.Executor;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public interface Future {

    Executor executor = null;

    boolean isDone();

    boolean isSucess();

    Throwable getException();

    void addListener(FutureListener listener);

    void await() throws InterruptedException;

    void awaitUninterruptly();

    void notifyListener();

}

package Future.DeadLockCheckFuture;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public class DeadLockFuture implements Future{

    private boolean done = false;
    private boolean sucess = false;
    private Throwable cause;
    private FutureListener listener;
    private volatile boolean enableDeadLockCheck;

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isSucess() {
        return sucess;
    }

    @Override
    public Throwable getException() {
        return null == cause ? null : cause;
    }

    @Override
    public void addListener(FutureListener listener) {

    }

    @Override
    public void await() throws InterruptedException {

    }

    @Override
    public void awaitUninterruptly() {

    }

    @Override
    public void notifyListener() {

    }
}

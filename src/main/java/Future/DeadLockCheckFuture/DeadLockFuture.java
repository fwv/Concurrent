package Future.DeadLockCheckFuture;

import tools.LogUtils;
import tools.NamedThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 * 带有死锁检测机制的异步Future
 */
public class DeadLockFuture implements Future{

    private boolean done = false;
    private boolean sucess = false;
    private Throwable cause;
    private FutureListener listener;
    private volatile boolean enableDeadLockCheck;
    private Executor executor;

    public DeadLockFuture(boolean useDeadLockCheck) {
        this.enableDeadLockCheck = useDeadLockCheck;
        this.executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(100),
                new NamedThreadFactory("DEAD-LOCK-FUTURE-THREAD"));
    }

    public DeadLockFuture(Executor executor, boolean useDeadLockCheck) {
        this.executor = executor;
        this.enableDeadLockCheck = useDeadLockCheck;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isSucess() {
        return sucess;
    }

    @Override
    public boolean setSuccess() {
        synchronized (this) {
           if (isDone() || isSucess()) {
               return false;
           }
            done = true;
            sucess = true;
        }
        notifyListener();
        return true;
    }

    @Override
    public Throwable getException() {
        return null == cause ? null : cause;
    }

    @Override
    public void addListener(FutureListener listener) {
        if (null == listener) {
            throw new NullPointerException("listener");
        }
        boolean notifyNow = false;
        synchronized (this) {
            if (isDone()) {
                notifyNow = true;
            }
            if (null == listener) {
                this.listener = listener;
            }
        }

        if (notifyNow) {
            notifyListener();
        }
    }

    @Override
    public Future await() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
            throw new InterruptedException();
        }

        synchronized (this) {
            checkDeadLock();
            while(!isDone()) {
                this.wait();
            }
        }
        return this;
    }

    @Override
    public Future awaitUninterruptly() {
        checkDeadLock();
        synchronized (this) {
            while(!isDone()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return this;
    }

    @Override
    public Future notifyListener() {
        try {
            listener.operationComplete(this);
        } catch (Exception e) {
            LogUtils.log.warn("A Exception was thrown by "+
            FutureListener.class.getSimpleName()+"."+e.toString());
        }
        return null;
    }

    private void checkDeadLock() {
        if (!enableDeadLockCheck && DeadLockProofWorker.PROOF.get() != null) {
            throw new IllegalStateException("Dead lock has happened, maybe it was caused by using DeadLockFuture#await() during" +
                    " FutureListener#operationComplete()");
        }
    }

    public void executor(Runnable task) {
        DeadLockProofWorker.start(this.executor, task, this);
    }

}

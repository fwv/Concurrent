package Future.DeadLockCheckFuture;

import java.util.concurrent.Executor;

/**
 * @Author fengwei
 * Created on 2016/11/8/0008.
 */
public final class DeadLockProofWorker {

    public static final ThreadLocal<Executor> PROOF = new ThreadLocal<Executor>();

    public static void start(Executor executor, Runnable runnable, Future future) {
        if (executor == null) {
            throw new NullPointerException("Executor is null");
        }
        if (runnable == null) {
            throw new NullPointerException("Runnable is null");
        }
        executor.execute(()->{
            PROOF.set(executor);
            try {
                runnable.run();
            } finally {
                future.setSuccess();
                PROOF.remove();
            }
        });
    }

    private DeadLockProofWorker() {

    }

}

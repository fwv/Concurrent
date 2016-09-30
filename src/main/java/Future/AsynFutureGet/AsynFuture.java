package Future.AsynFutureGet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author fengwei
 * Created on 2016/9/28/0028.
 * �첽Future��ͨ����Ӽ�����ʵ��
 */
public class AsynFuture {

    public ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Future future;

    private AsynFuture(Future f) {
        future = f;
    }

    public static AsynFuture create(Future future) {
        return new AsynFuture(future);
    }

    // ��Ӽ�����
    public void addListener(FutureListener listener) {
        Runnable task = ()->{
            try {
                Object result =  future.get();
                listener.onSuccess(result);
            } catch (Exception e) {
                listener.onFailed();
            }finally {
                shutdown();
            }
        };

        executorService.submit(task);
    }

    // ��Ӽ����������г�ʱ���ƣ�
    public void addListener(FutureListener listener, long timeout, TimeUnit unit) {
        Runnable task = ()->{
            try {
                Object result =  future.get(timeout, unit);
                listener.onSuccess(result);
            } catch (Exception e) {
                listener.onFailed();
            }finally {
                shutdown();
            }
        };

        executorService.submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }

}

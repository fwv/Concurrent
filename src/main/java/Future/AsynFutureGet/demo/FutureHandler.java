package Future.AsynFutureGet.demo;

import Future.AsynFutureGet.AsynFuture;
import Future.AsynFutureGet.FutureListener;
import tools.LogUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author fengwei
 * Created on 2016/9/28/0028.
 * �첽Future������ʹ��Future.get()��Ϊ�첽���ӿ�handler��Ӧ�ٶ�
 */
public class FutureHandler {

    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void process() throws ExecutionException, InterruptedException {
        ListenTask task = new ListenTask();
        Future f = executorService.submit(task);
        LogUtils.log.info("start process task");

            //ͬ��
            //String result = (String) f.get();
            //LogUtils.log.info(result);

            //�첽
            AsynFuture.create(f).addListener(new FutureListener<String>() {
                @Override
                public void onSuccess(String s) {
                    LogUtils.log.info(s);
                }

                @Override
                public void onFailed() {
                    LogUtils.log.info("throw exception");
                }
            });

        LogUtils.log.info("continue process another task");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureHandler.process();
    }

}

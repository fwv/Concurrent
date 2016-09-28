package Future.AsynFutureGet.demo;

import java.util.concurrent.Callable;

/**
 * @Author fengwei
 * Created on 2016/9/28/0028.
 */
public class ListenTask implements Callable<String>{

    @Override
    public String call() throws Exception {
        Thread.sleep(5000);
        return "call finish!";
    }

}

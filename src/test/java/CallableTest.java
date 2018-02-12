import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.*;

/**
 * Created by huangrui on 2018/1/17.
 */
@RunWith(JUnit4.class)
public class CallableTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Callable<String> ca = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                return "a";
            }
        };

        FutureTask<String> task = new FutureTask<>(ca);

        new Thread(task).start();
        System.out.println("这里是不会阻塞的");
        String s = task.get();
        System.out.println("调用 FutureTask.get()会阻塞， s=" + s);
    }
}

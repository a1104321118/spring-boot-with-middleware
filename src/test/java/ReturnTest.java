import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by huangrui on 2018/1/17.
 */
@RunWith(JUnit4.class)
public class ReturnTest {

    int a = 1;


    public int test1(){
        return ++a; //先++ 再return
    }

    public int test2(){
        return a++; //先return，再++
    }

    @Test
    public void test(){
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(a);
    }
}

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by huangrui on 2018/1/15.
 */
@RunWith(JUnit4.class)
public class IntegerTest {

    @Test
    public void test() {
        Integer a = 1;
        Integer b = 1;
        System.out.println(a == b); // -128到127 有缓存，所以会相等

        Integer a1 = 128;
        Integer b1 = 128;
        System.out.println(a1 == b1);


        Integer a2 = Integer.valueOf(1);
        Integer b2 = Integer.valueOf(1);// -128到127 有缓存，所以会相等
        System.out.println(a2 == b2);

        Integer a3 = Integer.valueOf(128);
        Integer b3 = Integer.valueOf(128);
        System.out.println(a3 == b3);

        Integer a4 = new Integer(1);
        Integer b4 = new Integer(1);
        System.out.println(a4 == b4);// 如果是new 对象，不使用缓存

        int i = 1;
        i = i + 1;

        short i1 = 1;
        i1 = (short) (i1 + 1);

        int k = 0;
        k = k + 1;

        System.out.println(int.class);
        System.out.println(Integer.class);

    }

    @Test
    public void test2(){
        A a = new A();
        B b = new B();
        System.out.println(a instanceof A);
        System.out.println(a instanceof B);
        System.out.println(b instanceof A);
        System.out.println(b instanceof B);
        System.out.println("====================");

        A a1 = new A();
        A b1 = new B();
        System.out.println(a1 instanceof A);
        System.out.println(a1 instanceof B);
        System.out.println(b1 instanceof A);
        System.out.println(b1 instanceof B);
    }
}

class A{}
class B extends A{}




import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huangrui on 2018/1/15.
 * 动态代理测试
 */
@RunWith(JUnit4.class)
public class ProxyTest {

    @Test
    public void test1(){
        final IA ia = new B();

        IA proxy = (IA)Proxy.newProxyInstance(IA.class.getClassLoader(), ia.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("test");
                return method.invoke(ia, args);//是代理ia 这个对象
            }
        });

        proxy.m();
        System.out.println(proxy.m2());


    }

    interface IA{
        void m();
        int m2();
    }

    class A implements IA {

        @Override
        public void m() {
            System.out.println("A");
        }

        @Override
        public int m2() {
            return 10;
        }
    }

    class B implements IA {

        @Override
        public void m() {
            System.out.println("B");
        }

        @Override
        public int m2() {
            return 20;
        }
    }

}

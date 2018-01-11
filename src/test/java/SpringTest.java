import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.util.concurrent.RateLimiter;
import com.hr.SpringBootDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by huangrui on 2018/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class SpringTest {

    /**
     * 布隆过滤器
     */
    @Test
    public void bloomFilterTest(){

        int testNum = 100000;

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), testNum, 0.001);

        Set<String> sets = new HashSet<String>(testNum);
        List<String> lists = new ArrayList<String>(testNum);

        for (int i = 0; i < testNum; i++) {
            String s = UUID.randomUUID().toString();
            bloomFilter.put(s);
            sets.add(s);
            lists.add(s);
        }

        int right = 0;
        int wrong = 0;
        for (int i = 0; i < 10000; i++) {
            String s = i % 100 == 0 ? lists.get(i % 100) : UUID.randomUUID().toString();
            if(bloomFilter.mightContain(s)){
                if (sets.contains(s)){
                    right++;
                }else {
                    wrong++;
                }
            }
        }

        System.out.println(right);
        System.out.println(wrong);
    }

    /**
     * 令牌桶 限流
     */
    @Test
    public void test2(){
        RateLimiter limiter = RateLimiter.create(10);//每秒放入10个令牌
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            limiter.acquire();//申请令牌，如果暂时没有，会阻塞等待
        }
        System.out.println(System.currentTimeMillis() - start);//将近10S的时间

        limiter.tryAcquire();//如果获取不到马上返回false
    }
}

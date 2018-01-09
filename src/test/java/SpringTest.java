import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hr.SpringBootDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * Created by huangrui on 2018/1/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class SpringTest {

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
}

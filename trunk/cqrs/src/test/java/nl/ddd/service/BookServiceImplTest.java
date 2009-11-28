package nl.ddd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext.xml")
public class BookServiceImplTest {

    @Resource
    protected SimpleJdbcTemplate jdbcTemplate;

    @Test
    public void testIt() {
        System.out.println(jdbcTemplate);
    }
}

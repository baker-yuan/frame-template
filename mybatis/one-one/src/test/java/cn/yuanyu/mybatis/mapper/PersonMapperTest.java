package cn.yuanyu.mybatis.mapper;


import cn.yuanyu.mybatis.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonMapperTest {

    @Resource
    private PersonMapper personMapper;

    @Test
    public void selectPersonById() {
        Person person = personMapper.selectPersonByUsername("jack");
        System.out.println(person);
    }
}
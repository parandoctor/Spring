package com.spring.demo;

import com.spring.demo.beans.User;
import com.spring.demo.beans.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring-01 单元测试
 */
class AppTest {

    @Test
    void testXmlApplicationContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 验证 Bean 不为 null
        User user = context.getBean("user", User.class);
        assertNotNull(user);
        assertEquals("张三", user.getName());
        assertEquals(Long.valueOf(1001), user.getId());

        // 验证 singleton 作用域
        User s1 = context.getBean("singletonBean", User.class);
        User s2 = context.getBean("singletonBean", User.class);
        assertSame(s1, s2, "singleton Bean 应是同一实例");

        // 验证 prototype 作用域
        User p1 = context.getBean("prototypeBean", User.class);
        User p2 = context.getBean("prototypeBean", User.class);
        assertNotSame(p1, p2, "prototype Bean 应是不同实例");

        // 验证 DI 注入
        UserService userService = context.getBean("userService", UserService.class);
        assertNotNull(userService);

        ((ClassPathXmlApplicationContext) context).close();
    }
}

package io.renren;

import io.chaoshua.RenrenApplication;
import io.chaoshua.modules.app.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RenrenApplication.class})
public class JwtTest {
    @Autowired
    private JwtUtils jwtUtils;
    @Test
    public void test() {
        String token = jwtUtils.generateToken(5);

        // userId 15
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNSIsImlhdCI6MTU1MTUxNjQ0NiwiZXhwIjoxNTUyMTIxMjQ2fQ.pCQnyr7lJYCL3jYq9F1YwHTAfg4CAwT8-Tq-xphBptM9d6P0C2t_jDf-2yedGIzt5j7sKMJoaS1zohQoKUP5XA
        // userId 1
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTUxNzU3NDE1LCJleHAiOjE1NTIzNjIyMTV9.gg7qN9-BYcqIrS2Zs2Od2XzEgP4NWbugLxEr2xBztO6KtKdCUv42Pqd2hIq5XBPfS6T0BmFAp4GUbP9s4j_2RA
        System.out.println(token);
    }

}

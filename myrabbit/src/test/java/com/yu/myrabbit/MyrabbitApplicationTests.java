package com.yu.myrabbit;

import com.yu.myrabbit.boot.BootSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyrabbitApplicationTests {

    @Autowired
    BootSend bootSend;

    @Test
    void contextLoads() {
        bootSend.send();

    }

}

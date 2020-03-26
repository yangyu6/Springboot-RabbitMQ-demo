package com.yu.bootrabbit;

import com.yu.bootrabbit.boot.BootSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootrabbitApplicationTests {

    @Autowired
    BootSend bootSend;

    @Test
    void send() {
        bootSend.send("haahshan");
    }
}

package com.yu.bootrabbit.controller;

import com.yu.bootrabbit.boot.BootSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class SendController {

    @Autowired
    private BootSend bootSend;

    @RequestMapping("send")
    public void send1 (String message){
        bootSend.send(message);
    }
}

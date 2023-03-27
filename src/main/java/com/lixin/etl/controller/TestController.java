package com.lixin.etl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-23 16:55:57
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-23     张李鑫                     1.0         1.0 Version
 */
@RestController
@RequestMapping("/test")
public class TestController {



    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}

package com.zhenwu.api;

import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhenwu
 */
@SpringBootApplication
class ApiApplicationTest {

    @Test
    void text() {
        System.out.println(UUID.randomUUID().toString(true));
    }
}

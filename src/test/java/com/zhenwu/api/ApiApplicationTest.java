package com.zhenwu.api;

import cn.hutool.core.lang.UUID;
import com.zhenwu.sdk.client.ApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author zhenwu
 */
@SpringBootTest
class ApiApplicationTest {

    @Resource
    private ApiClient apiClient;

    @Test
    void text() {
        System.out.println(UUID.randomUUID().toString(true));
    }

    @Test
    void sdkTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString(true));
        }
    }
}

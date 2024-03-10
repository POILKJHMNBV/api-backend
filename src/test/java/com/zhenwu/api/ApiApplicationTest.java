package com.zhenwu.api;

import cn.hutool.core.lang.UUID;
import com.zhenwu.sdk.client.ApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author zhenwu
 */
@SpringBootTest
class ApiApplicationTest {

    @Resource
    private ApiClient apiClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    @Test
    void userOfflineTes() {
        DefaultRedisScript<String> userOfflineScript = new DefaultRedisScript<>();
        userOfflineScript.setLocation(new ClassPathResource("user_offline.lua"));
        userOfflineScript.setResultType(String.class);
        String[] args = new String[]{"123", "4534"};
        String res = this.stringRedisTemplate.execute(userOfflineScript, Collections.emptyList(), args);
        System.out.println(res);
    }
}

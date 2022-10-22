package com.tidb.hackathon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;

    @Value("${spring.application.name}")
    private String application;

    // 做数据初始化
    @Override
    public void run(ApplicationArguments args) {
        if (this.context.isActive()) {
            log.info("  _   _   _   _   _   _   _   _");
            log.info(" / \\ / \\ / \\ / \\ / \\ / \\ / \\ / \\");
            log.info("( c | o | m | p | l | e | t | e )");
            log.info(" \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/");
            log.info("{} 启动完毕,时间:{}", this.application, LocalDateTime.now());
        }
    }

}

package com.codesquad.secondhand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        // 로컬 환경에서 aws sdk 에러 나지 않도록 설정
        exclude = {
                org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
        }
)
public class SecondHandApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondHandApplication.class, args);
    }

}

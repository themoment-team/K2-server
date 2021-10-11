package com.moment.the;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@Slf4j
public class TheApplication {
	/**
	 * springboot를 한국 표준시로 설정합니다.
	 * `@PostConstruct`를 사용하여 메소드를 단 한번만 호출 되도록 합니다.
	 * @author 전지환
	 */
	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		log.info("========== server start time: {}", new Date());
	}
	public static void main(String[] args) {
		SpringApplication.run(TheApplication.class, args);
	}
}


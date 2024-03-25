package study_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 엔티티 생성시간, 업뎃시간 자동변경
@SpringBootApplication
public class SpringBootDeveloperApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDeveloperApplication.class, args);
	}
}

package study_spring.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
//@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티값을 가져와서 사용 (@Value 와 다른점은?)
public class JwtProperties {
	@Value("${jwt.issuer}")
	private String issuer;
	@Value("${jwt.secret_key}")
	private String secretKey;
}

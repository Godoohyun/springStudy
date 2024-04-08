package study_spring;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 엔티티 생성시간, 업뎃시간 자동변경
@SpringBootApplication
public class SpringBootDeveloperApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDeveloperApplication.class, args);
		
		// BASE64 인코더에 대해 콘솔찍어보기
		String original = "Hello, World!";
        
        // URL 안전한 Base64 인코딩
        String encoded = Base64.getUrlEncoder().encodeToString(original.getBytes()); // 여기에 UTF-8 설정을 추가로 할 수 있다
        try {
			String encoded2 = Base64.getUrlEncoder().encodeToString(original.getBytes("UTF-8")); // 예외를 전달할 곳이 없기때문에 여기서 처리
			System.out.println("Encoded2: " + encoded2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} //
        System.out.println("Encoded: " + encoded);
        
        // URL 안전한 Base64 디코딩
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        String decoded = new String(decodedBytes);
        System.out.println("Decoded: " + decoded);
	}
}

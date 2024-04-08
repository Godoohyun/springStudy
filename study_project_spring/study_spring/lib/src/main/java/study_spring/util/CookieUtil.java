package study_spring.util;

import java.util.Base64;

import org.springframework.util.SerializationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	// 이름, 값, 수명으로 쿠키세팅
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");		// 루트경로 -> 해당 설정으로 쿠키가 사용 될 수 있는 Path를 설정할 수 있다. 
		cookie.setMaxAge(maxAge); 	// 쿠키의 수명 설정
		response.addCookie(cookie); // 설정된 쿠키 브라우저에 넘겨    
		// response 를 통해 쿠키를 만들어서 보내면 브라우저에서는 자동저장된다.
	}
	
	// 쿠키 삭제 
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return; // 애초에 쿠키가 없으니 걍 리턴
		}
		
		for(Cookie cookie : cookies) {
			if(name.equals(cookie.getName())) {
				cookie.setValue(""); // value값을 초기화
				cookie.setPath("/"); // 루트경로 
				cookie.setMaxAge(0); // 수명끝
				response.addCookie(cookie);
			}
		}
	}
	
	// 직렬화(객체를 JSON등의 형태로 만들어) 쿠키의 value에 넣을 수 있다.
	public static String serialize(Object obj) {
		return Base64.getUrlEncoder() // Base64 인코딩은 JAVA8 부터 사용
				.encodeToString(SerializationUtils.serialize(obj)); // 객체 직렬화
	}
	
	// 위와 반대로 역질렬화 쿠키를 객체로 해석하기 위해
	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(
				SerializationUtils.deserialize(
						Base64.getUrlDecoder().decode(cookie.getValue())
						)
				);
	}
	
}
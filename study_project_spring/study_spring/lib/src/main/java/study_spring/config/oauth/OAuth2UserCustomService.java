package study_spring.config.oauth;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study_spring.domain.User;
import study_spring.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User user = super.loadUser(userRequest);
		saveOrUpdate(user);
		return user;
	}
	
	// 유저유무에 따른 업데이트
	private User saveOrUpdate(OAuth2User oAuth2User) {
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email = (String) attributes.get("email");
		String name = (String) attributes.get("name");
		// 빌더빌더
		User user = userRepository.findByEmail(email)
				.map(entity -> entity.update(name))  // 업뎃하거나
				.orElse(User.builder()				 // 새로 만들거나
						.email(email)
						.nickname(name)
						.build());
		return userRepository.save(user); // 바뀐정보를 바탕으로 저장
	}
}

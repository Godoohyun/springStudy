package study_spring.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study_spring.domain.User;
import study_spring.dto.AddUserRequest;
import study_spring.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
//	private final BCryptPasswordEncoder bCryptPasswordEncoder; 내부에서 생성할꺼
	
	public Long save(AddUserRequest dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return userRepository.save(User.builder()
				.email(dto.getEmail())
				.password(encoder.encode(dto.getPassword()))
				.build()).getId();
	}
	
	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
	
}

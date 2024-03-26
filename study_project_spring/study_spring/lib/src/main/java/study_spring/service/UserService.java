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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Long save(AddUserRequest dto) {
		return userRepository.save(User.builder()
				.email(dto.getEmail())
				.password(bCryptPasswordEncoder.encode(dto.getPassword()))
				.build()).getId();
	}
}

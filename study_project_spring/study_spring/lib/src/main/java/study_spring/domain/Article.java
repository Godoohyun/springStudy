package study_spring.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {
	
	@Id // 기본키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false)
	private String content;
	
	@Builder // 빌더패턴 (받아오는 값으로 생성)
	public Article (String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	@CreatedDate // 엔티티 생성시각
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@LastModifiedDate // 엔티티 수정시각
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	
//	// 기본생성자 @@NoArgsConstructor 로 대체
//	protected Article() {
//	}
//	
//	// Getter
//	public Long getId() {
//		return id;
//	}
//	
//	// Getter
//	public String getTitle() {
//		return title;
//	}
//	
//	// Getter
//	public String getContent() {
//		return content;
//	}
	
}

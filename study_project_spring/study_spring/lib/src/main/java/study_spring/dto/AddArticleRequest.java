package study_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study_spring.domain.Article;

@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 필드값을 파라미터로 받는다
@Getter
public class AddArticleRequest {
	
	private String title;
	private String content;
	
	public Article ToEntity() {
		// 빌더패턴을 활용
		return Article.builder()
				.title(title)
				.content(content)
				.build();
	}
}

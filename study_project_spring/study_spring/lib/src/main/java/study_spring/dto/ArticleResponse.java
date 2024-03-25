package study_spring.dto;

import lombok.Getter;
import study_spring.domain.Article;

@Getter
public class ArticleResponse {
	
	private final String title;
	private final String content;
	
	public ArticleResponse(Article article) {
		this.title = article.getTitle();
		this.content = article.getTitle();
	}
	
}

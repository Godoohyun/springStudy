package study_spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study_spring.domain.Article;
import study_spring.dto.AddArticleRequest;
import study_spring.dto.UpdateArticleRequest;
import study_spring.repository.BlogRepository;

@RequiredArgsConstructor // 
@Service
public class BlogService {
	
	private final BlogRepository blogRepository;
	
	public Article save(AddArticleRequest request) {
		return blogRepository.save(request.ToEntity());
	}
	
	public List<Article> findAll(){
		return blogRepository.findAll();
	}
	
	public Article findById(long id) {
		return blogRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("not found: " + id));
	}
	
	public void delete(long id) {
		blogRepository.deleteById(id);
	}

	@Transactional
	public Article update(long id, UpdateArticleRequest request) {
		Article article = blogRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("no found : " + id));
		
		article.update(request.getTitle(), request.getContent());
		return article;
	}
}

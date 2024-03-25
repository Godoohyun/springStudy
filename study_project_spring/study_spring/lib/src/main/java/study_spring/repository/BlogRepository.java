package study_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study_spring.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {
}

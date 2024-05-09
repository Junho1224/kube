package com.von.api.article.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

import com.von.api.article.model.Article;
import com.von.api.article.model.ArticleDTO;
import com.von.api.article.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    private ArticleService service;
    private static Article testArticle;
    @Mock
    private ArticleRepository repository;

    @BeforeEach
    void setup() {
        this.service = new ArticleServiceImpl(repository);
    }

    @BeforeEach
    void init() {
        testArticle = Article.of(1L, "테스트 제목", "글");

    }

    // @Test
    // public void 게시글_제목_검색()throws Exception{
    // repository.save(testArticle);

    // Article article = repository.findById(1L).get();

    // assertThat(article.getTitle()).isEqualTo("테스트 제목");
    // }

    @Test
    public void 게시글_제목_검색() throws Exception {
        Long articleId = 1L;
        // findById가 testArticle을 반환하도록 설정
        BDDMockito.given(repository.findById(articleId)).willReturn(Optional.of(testArticle));

        // 테스트 실행
        Article article = repository.findById(articleId).orElseThrow(() -> new Exception("Article not found"));

        // 검증
        assertThat(article.getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    public void 게시글_전체_검색() throws Exception {
        List<Article> articles = getList();
        BDDMockito.given(repository.findAll()).willReturn(articles);
        List<ArticleDTO> list = service.findAll();
        assertThat(list.size()).isEqualTo(3);

    }

    private List<Article> getList() {
        return Arrays.asList(
                Article.builder().id(1L).title("null").content("null1").build(),
                Article.builder().id(1L).title("null2").content("null3").build(),
                Article.builder().id(1L).title("null4").content("null5").build());
    }

}

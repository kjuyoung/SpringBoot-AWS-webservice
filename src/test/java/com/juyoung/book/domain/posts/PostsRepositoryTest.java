package com.juyoung.book.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsRepositoryTest {

    @Autowired
    PostsRepository pr;

    @AfterEach
    public void cleanup() {
        pr.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        pr.save(Posts.builder()
                .title(title)
                .content(content)
                .author("kjuyoung@nate.com")
                .build());

        //when
        List<Posts> postsList = pr.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    void BaseEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2022, 9, 27, 0, 0, 0);
        pr.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = pr.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>> CreatedDate = " + posts.getCreatedDate() + ", modifiedData = " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}

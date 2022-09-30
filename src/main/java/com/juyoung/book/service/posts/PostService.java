package com.juyoung.book.service.posts;

import com.juyoung.book.domain.posts.Posts;
import com.juyoung.book.domain.posts.PostsRepository;
import com.juyoung.book.web.dto.PostsListReponseDto;
import com.juyoung.book.web.dto.PostsResponseDto;
import com.juyoung.book.web.dto.PostsSaveRequestDto;
import com.juyoung.book.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostsRepository pr;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return pr.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = pr.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = pr.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        pr.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = pr.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListReponseDto> findAllDesc() {
        return pr.findAllDesc().stream()
                .map(PostsListReponseDto::new)
                .collect(Collectors.toList());
    }
}

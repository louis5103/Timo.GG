package com.tools.seoultech.timoproject.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tools.seoultech.timoproject.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Post post = Post.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("user"+ (i % 10))
                    .build();
            System.out.println(postRepository.save(post));
        });
    }
}
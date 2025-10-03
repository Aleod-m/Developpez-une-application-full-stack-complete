package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.PostSummary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {

    @Query("SELECT new com.openclassrooms.mddapi.domain.PostSummary(p.id, p.title, p.author.username, p.topic.label, SUBSTRING(p.content, 1, 100), p.createdAt) FROM Post p")
	List<PostSummary> findAllSummary();

}

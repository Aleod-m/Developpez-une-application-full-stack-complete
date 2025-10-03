package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.domain.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

	List<Comment> findByPostId(Long postId);

}

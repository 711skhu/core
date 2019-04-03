package com.shouwn.oj.repository.problem;

import com.shouwn.oj.model.entity.problem.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}

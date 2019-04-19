package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom, MemberRepository<Student, Long> {
}

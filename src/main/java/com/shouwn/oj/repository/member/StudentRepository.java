package com.shouwn.oj.repository.member;

import com.shouwn.oj.model.entity.member.Student;

public interface StudentRepository extends StudentRepositoryCustom, MemberRepository<Student, Long> {
}

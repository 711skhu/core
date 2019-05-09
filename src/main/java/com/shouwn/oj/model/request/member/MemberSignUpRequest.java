package com.shouwn.oj.model.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpRequest {

	private String name;

	private String username;

	private String password;

	private String email;
}

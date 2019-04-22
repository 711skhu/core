package com.shouwn.oj.model.request.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequest {

	private String username;

	private String password;
}

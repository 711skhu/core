package com.shouwn.oj.model.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReissueTokenRequest {

	private String refreshToken;
}

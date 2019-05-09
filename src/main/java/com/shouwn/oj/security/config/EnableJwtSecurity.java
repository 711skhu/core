package com.shouwn.oj.security.config;

import java.lang.annotation.*;

import com.shouwn.oj.config.security.JwtSecurityConfig;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * SpringSecurity 와 Jwt 설정을 import 하는 enable 설정 어노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Import({JwtSecurityConfig.class})
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,    // @Secured
		jsr250Enabled = true,    // @RolesAllowed
		prePostEnabled = true    // @PreAuthorize
)
public @interface EnableJwtSecurity {
}

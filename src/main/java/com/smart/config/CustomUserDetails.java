package com.smart.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entities.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	 User user;
	// trả về danh sách các quyền của người dùng
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}

	 //trả về password đã dùng trong qúa trình xác thực
	@Override
	public String getPassword() {

		return user.getPassword();
	}
	//trả về username đã dùng trong qúa trình xác thực
	@Override
	public String getUsername() {

		return user.getEmail();
	}
	// trả về true nếu tài khoản của người dùng chưa hết hạn
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	//trả về true nếu người dùng chưa bị khóa
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	//trả về true nếu chứng thực (mật khẩu) của người dùng chưa hết hạn
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}
	// trả về true nếu người dùng đã được kích hoạt	
	@Override
	public boolean isEnabled() {
		return true;
	}

}

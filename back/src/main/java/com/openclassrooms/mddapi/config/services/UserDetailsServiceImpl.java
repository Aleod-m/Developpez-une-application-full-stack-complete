package com.openclassrooms.mddapi.config.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.mddapi.domain.entity.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	UserRepository userRepository;

	UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.or(() -> this.userRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

		return UserDetailsImpl
				.builder()
				.id(user.getId())
				.username(user.getEmail())
				.password(user.getPassword())
				.build();
	}

}

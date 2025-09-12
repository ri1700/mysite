package com.bit2025.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.UserRepository;
import com.bit2025.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void join(UserVo userVo) {
		System.out.println("Before Join: " + userVo);
		userRepository.insert(userVo);
		System.out.println("After Join:" + userVo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(UserVo userVo) {
		return getUser(userVo.getEmail(), userVo.getPassword());
	}

	public UserVo getUser(Long id) {
		return userRepository.findById(id);
	}

	public UserVo getUser(String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUser(UserVo userVo) {
		userRepository.update(userVo);
	}


}
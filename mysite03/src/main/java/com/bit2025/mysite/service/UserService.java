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
		System.out.println("Before Join:" + userVo);
		userRepository.insert(userVo);
		System.out.println("After Join:" + userVo);
	}
	public UserVo getUser(UserVo userVo) {
		return userRepository.findByEmailAndPassword(userVo.getEmail(), userVo.getPassword());
	}
	public UserVo getUser(Long id) {
		return userRepository.findById(id);
	}
	public void updateUser(UserVo userVo) {
		userRepository.update(userVo);
	}
}
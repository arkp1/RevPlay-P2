package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class UserDTO {
	  private Long userId;
	  private String userName;
	  private String email;
	  private String userPassword;
	  private String displayName;
	  private String bio;
	  private String profileImage;
	  private String securityQuestion;
	  private String securityAnswer;
	  private String role;
	  private LocalDateTime createdAt = LocalDateTime.now();
	public UserDTO() {
	}
	public UserDTO(Long userId, String userName, String email, String userPassword, String displayName, String bio,
			String profileImage, String securityQuestion, String securityAnswer, String role, LocalDateTime createdAt) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.userPassword = userPassword;
		this.displayName = displayName;
		this.bio = bio;
		this.profileImage = profileImage;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
		this.role = role;
		this.createdAt = createdAt;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	  
	  
	  
}

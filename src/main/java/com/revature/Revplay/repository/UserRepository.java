package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	


}
package com.tsanyi.iApp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tsanyi.iApp.db.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByName(String name);
	
	@Query("SELECT u.id FROM User u WHERE u.name= ?1")
	List<Long> findIdsByName(String name);
	
}

package com.tsanyi.iApp.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tsanyi.iApp.db.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
//	List<User> findByName(String name);
	
//	@Query("SELECT u.id FROM User u WHERE u.name= ?1")
//	List<Long> findIdsByName(String name);
	
//	@Query("SELECT u FROM User u WHERE (?1 IS NULL OR u.name=?1) AND (?2 IS NULL OR ?3 IS NULL OR u.birthDate BETWEEN ?2 AND ?3)")
//	List<User> findUsersByFilter(String name, Date birthDateStart, Date birthDateEnd);
}

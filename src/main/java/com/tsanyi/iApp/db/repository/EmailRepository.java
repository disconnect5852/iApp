package com.tsanyi.iApp.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.tsanyi.iApp.db.entity.Email;

public interface EmailRepository extends CrudRepository<Email, Long> {
	
}

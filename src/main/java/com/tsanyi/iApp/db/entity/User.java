package com.tsanyi.iApp.db.entity;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthDate;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@Cascade(CascadeType.ALL)
	private Set<Email> email;

	public User(String name, Date birthDate, Set<Email> email) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
	}

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Set<Email> getEmail() {
		return email;
	}

	public void setEmail(Set<Email> email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", " + (name != null ? "name=" + name + ", " : "") + (birthDate != null ? "birthDate=" + birthDate + ", " : "")
				+ (email != null ? "email=" + email.stream().map(mail -> mail.getEmail()).collect(Collectors.joining(";", "{", "}")) : "") + "]";
	}
}

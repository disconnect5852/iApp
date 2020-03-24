package com.tsanyi.iApp.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {
	@NotNull
	@Size(min = 4, max = 254)
	private String name;
	@NotNull
	private Date birthDate;
	private Set<@Email String> emails;
	
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
	public Set<String> getEmails() {
		return emails;
	}
	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public UserDTO(@NotNull @Size(min = 4, max = 254) String name, @NotNull Date birthDate, Set<@Email String> emails) {
		this.name = name;
		this.birthDate = birthDate;
		this.emails = emails;
	}
	public UserDTO() {
		this.emails=new HashSet<String>();
	}
	
	@Override
	public String toString() {
		return "UserDTO [" + (name != null ? "name=" + name + ", " : "") + (birthDate != null ? "birthDate=" + birthDate + ", " : "") + (emails != null ? "emails=" + String.join("; ", emails)  : "") + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

package com.preeplus.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 7427307938593028287L;

	@Id
	@GeneratedValue
	private Long id;

	private String roleName;

	public Long getId() {
		return this.id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

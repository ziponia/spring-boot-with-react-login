package com.preeplus.api.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_user")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 935977961610289848L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String password;

	@JoinTable(
			name = "tbl_user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	@OneToMany(targetEntity = RoleEntity.class)
	private List<RoleEntity> userRoles;

	@CreationTimestamp
	private Date createTime;

	@UpdateTimestamp
	private Date updateTime;

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public List<RoleEntity> getUserRoles() {
		return this.userRoles;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserRoles(List<RoleEntity> userRoles) {
		this.userRoles = userRoles;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

package com.preeplus.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LoginParams implements Serializable {
	private static final long serialVersionUID = 7710662072308044971L;

	private String username;
	private String password;
	private List<String> authorizes;
}

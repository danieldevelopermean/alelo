package br.com.test.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {

	private Integer code;
	private String message;

	public Message(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}

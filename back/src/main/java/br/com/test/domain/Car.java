package br.com.test.domain;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Car {

	private Long id;

	@NotEmpty(message = "Mandatory")
	private String placa;

	@NotEmpty(message = "Mandatory")
	private String modelo;

	@NotEmpty(message = "Mandatory")
	private String marca;

	@NotEmpty(message = "Mandatory")
	private String cor;

	private String status;

	public Car(Long id, String placa, String modelo, String marca, String cor, String status) {
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.cor = cor;
		this.status = status;
	}

}

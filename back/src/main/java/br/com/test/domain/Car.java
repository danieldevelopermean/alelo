package br.com.test.domain;

import javax.validation.constraints.NotEmpty;

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

	public Car() {}
	
	public Car(Long id, String placa, String modelo, String marca, String cor, String status) {
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.cor = cor;
		this.status = status;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

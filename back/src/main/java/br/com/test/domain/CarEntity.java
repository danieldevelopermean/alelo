package br.com.test.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.test.enums.Enum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String placa;
	private String modelo;
	private String marca;
	private String cor;
	private Enum status;

	public CarEntity(String placa, String modelo, String marca, String cor, Enum status) {
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.cor = cor;
		this.status = status;
	}

}

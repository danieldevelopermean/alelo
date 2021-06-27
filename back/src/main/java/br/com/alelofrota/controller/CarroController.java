package br.com.alelofrota.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.alelofrota.domain.dto.CarroDTO;
import br.com.alelofrota.domain.dto.ErrorMessage;
import br.com.alelofrota.service.CarroService;

import javax.validation.Valid;


@RestController
@RequestMapping("/placas")
@CrossOrigin(origins = "*")
public class CarroController {
	
	private static final Logger LOGGER = Logger.getLogger(CarroController.class.getName());
	
	@Autowired
	private CarroService carroService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody CarroDTO car) {
		LOGGER.log(Level.INFO, "Cadastra novo produto.");

		boolean existCar = carroService.busaPorPlacaContains(car.getPlaca());

		if(existCar){
			return new ResponseEntity<>(new ErrorMessage(400, "Veículo ja cadastrado: " + car.getPlaca()), HttpStatus.CONFLICT);
		}

		if(car != null && !car.getPlaca().isEmpty()) {
			CarroDTO newProduct = carroService.create(car);
			
			if(newProduct != null) {
				return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new ErrorMessage(400, "Erro ao cadastrar carro: " + car.getPlaca()), HttpStatus.BAD_REQUEST);
			}
		}
		
		return new ResponseEntity<>(new ErrorMessage(400, "Erro ao cadastrar carro: " + car.getPlaca()), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/search/{placa}", method = RequestMethod.GET)
	public ResponseEntity<List<CarroDTO>> buscaPorPlaca(@PathVariable String placa) {
		LOGGER.log(Level.INFO, "Busca carro pela placa.");

		return ResponseEntity.ok(carroService.busaPorPlaca(placa));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CarroDTO>> listCars() {
		LOGGER.log(Level.INFO, "Lista carros.");
		
		return ResponseEntity.ok(carroService.list());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findByIdProduct(@PathVariable Long id){
		LOGGER.log(Level.INFO, "Busca carro por ID: " + id);
		
		try {
			CarroDTO resultCar = carroService.findByIdCar(id);
			
			if(resultCar != null) {
				return new ResponseEntity<>(resultCar, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		return new ResponseEntity<>(new String("Carro não foi localizado!"), HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public  ResponseEntity<?> update(@Valid @RequestBody CarroDTO car, @PathVariable Long id) {
		LOGGER.log(Level.INFO, "Atualizando o carro: " + car.getPlaca());
		
		CarroDTO productUpdate = carroService.update(car, id);
		
		if(productUpdate != null) {
			return new ResponseEntity<>(productUpdate, HttpStatus.OK);
		}
			
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public  ResponseEntity<?> delete(@PathVariable Long id) {
		LOGGER.log(Level.INFO, "Deletando o carro com o id: " + id);
		
		if(carroService.delete(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
			
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

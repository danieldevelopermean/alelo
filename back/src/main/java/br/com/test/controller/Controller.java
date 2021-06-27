package br.com.test.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.domain.dto.Car;
import br.com.test.domain.dto.Message;
import br.com.test.service.CarroService;

@RestController
@RequestMapping("/placas")
@CrossOrigin(origins = "*")
public class Controller {

	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

	@Autowired
	private CarroService service;

	@PostMapping
	public ResponseEntity<?> createCar(@Valid @RequestBody Car carro) {

		boolean carroExiste = service.busaPorPlacaContains(carro.getPlaca());

		if (carroExiste) {
			return new ResponseEntity<>(new Message(404, "Carro no Sistema" + carro.getPlaca()), HttpStatus.CONFLICT);
		}

		if (carro != null && !carro.getPlaca().isEmpty()) {
			Car newProduct = service.create(carro);

			if (newProduct != null) {
				return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new Message(400, "Error: " + carro.getPlaca()), HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<>(new Message(400, "Error: " + carro.getPlaca()), HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/search/{identificacao}")
	public ResponseEntity<List<Car>> findSign(@PathVariable String placa) {
		return ResponseEntity.ok(service.busaPorPlaca(placa));
	}

	@GetMapping
	public ResponseEntity<List<Car>> list() {
		return ResponseEntity.ok(service.list());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			Car resultCar = service.findByIdCar(id);

			if (resultCar != null) {
				return new ResponseEntity<>(resultCar, HttpStatus.OK);
			}

		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error: " + id);
		}

		return new ResponseEntity<>(new String("Não Encontrado"), HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateCar(@Valid @RequestBody Car car, @PathVariable Long id) {
		Car update = service.update(car, id);

		if (update != null) {
			return new ResponseEntity<>(update, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> erase(@PathVariable Long id) {
		if (service.delete(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

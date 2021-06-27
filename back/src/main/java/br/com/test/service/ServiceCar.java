package br.com.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.test.controller.Controller;
import br.com.test.domain.Car;
import br.com.test.domain.CarEntity;
import br.com.test.enums.Enum;
import br.com.test.repositories.CarroRepository;

@Service
public class ServiceCar {

	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

	@Autowired
	private CarroRepository carroRepository;

	public List<Car> list() {
		List<CarEntity> cars = carroRepository.findAll();
		List<Car> carsDto = new ArrayList<Car>();

		for (CarEntity car : cars) {
			Car cartDTO = new Car(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(),
					(car.getStatus() == Enum.ACTIVE) ? "Ativo" : "Inativo");
			carsDto.add(cartDTO);
		}

		return carsDto;
	}

	public List<Car> findBy(String placa) {

		List<CarEntity> cars = carroRepository.findByPlaca(placa);
		List<Car> carsDto = new ArrayList<Car>();

		for (CarEntity car : cars) {
			Car cartDTO = new Car(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(),
					(car.getStatus() == Enum.ACTIVE) ? "Ativo" : "Inativo");
			carsDto.add(cartDTO);
		}

		return carsDto;
	}

	public boolean findByPlaca(String placa) {

		CarEntity car = carroRepository.findByPlacaContaining(placa);

		if (car != null) {
			return true;
		}
		return false;
	}

	public Car findById(Long id) {
		Optional<CarEntity> obj = carroRepository.findById(id);
		Car carDTO = new Car(obj.get().getId(), obj.get().getPlaca(), obj.get().getModelo(), obj.get().getMarca(),
				obj.get().getCor(), (obj.get().getStatus() == Enum.ACTIVE) ? "Ativo" : "Inativo");

		return carDTO;
	}

	public Car create(Car car) {
		CarEntity carSave = carroRepository.save(createObjectCarro(car));
		return new Car(carSave.getId(), carSave.getPlaca(), carSave.getModelo(), carSave.getMarca(), carSave.getCor(),
				(carSave.getStatus() == Enum.ACTIVE) ? "Active" : "Inactive");
	}

	public Car update(Car car, Long id) {

		try {
			Car findByIdCar = findById(id);

			if (findByIdCar != null) {
				CarEntity carUpdate = createObjectCarro(car);

				carUpdate.setId(id);
				carUpdate = carroRepository.save(carUpdate);
				return new Car(carUpdate.getId(), carUpdate.getPlaca(), carUpdate.getModelo(), carUpdate.getMarca(),
						carUpdate.getCor(), (carUpdate.getStatus() == Enum.ACTIVE) ? "Ativo" : "Inativo");
			}

		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}

		return null;
	}

	public boolean delete(Long id) {

		try {
			Car findByIdCar = findById(id);

			if (findByIdCar != null) {
				CarEntity productDelete = createObjectCarro(findByIdCar);

				productDelete.setId(id);
				carroRepository.delete(productDelete);

				return true;
			}

		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}

		return false;
	}

	private CarEntity createObjectCarro(Car car) {
		return new CarEntity(car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(),
				("ativo".equals(car.getStatus()) ? Enum.ACTIVE : Enum.INACTIVE));
	}

}

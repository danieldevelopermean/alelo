package br.com.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.test.controller.Controller;
import br.com.test.domain.Carro;
import br.com.test.domain.dto.Car;
import br.com.test.enuns.StatusEnum;
import br.com.test.repositories.CarroRepository;

@Service
public class CarroService {
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
	
	@Autowired
	private CarroRepository carroRepository;
	
	/**
	 * Método que lista todos os carros
	 * @return retorna uma lista de carros do tipo CarroDTO
	*/
	public List<Car> list(){
		List<Carro> cars = carroRepository.findAll();
		List<Car> carsDto = new ArrayList<Car>();
		
		for (Carro car : cars) {
			Car cartDTO = new Car(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), (car.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
			carsDto.add(cartDTO);
		}
		
		return carsDto;
	}

	/**
	 * Método que faz busca pela placa do veículo
	 * @param placa
	 * @return
	 */
	public List<Car> busaPorPlaca(String placa) {

		List<Carro> cars = carroRepository.findByPlaca(placa);
		List<Car> carsDto = new ArrayList<Car>();

		for (Carro car : cars) {
			Car cartDTO = new Car(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), (car.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
			carsDto.add(cartDTO);
		}

		return carsDto;
	}

	public boolean busaPorPlacaContains(String placa) {

		Carro car = carroRepository.findByPlacaContaining(placa);

		if(car != null){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Método que busca um carro pelo ID
	 * @param id
	 * @return retorna um carroDTO 
	*/
	public Car findByIdCar(Long id) {
		Optional<Carro> obj = carroRepository.findById(id);
		Car carDTO = new Car(obj.get().getId(), obj.get().getPlaca(), obj.get().getModelo(), obj.get().getMarca(), obj.get().getCor(), (obj.get().getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
		
		return carDTO;
	}
	
	
	/**
	 * Método que cria um carro 
	 * @param car
	 * @return retorna carroDTO do carro criado
	*/
	public Car create(Car car) {
		Carro carSave = carroRepository.save(createObjectCarro(car));
		return new Car(carSave.getId(), carSave.getPlaca(), carSave.getModelo(), carSave.getMarca(), carSave.getCor(), (carSave.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
	}
	
	
	/**
	 * Método que atualiza o carro a partir do ID
	 * @param car
	 * @param id
	 * @return retorna carroDTO do carro atualizado
	*/
	public Car update(Car car, Long id) {
		
		try {
			Car findByIdCar = findByIdCar(id);
			
			if(findByIdCar != null) {
				Carro carUpdate = createObjectCarro(car);
				
				carUpdate.setId(id);
				carUpdate = carroRepository.save(carUpdate);
				return new Car(carUpdate.getId(), carUpdate.getPlaca(), carUpdate.getModelo(), carUpdate.getMarca(), carUpdate.getCor(), (carUpdate.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		
		return null;
	}
	
	/**
	 * Método que deleta um carro
	 * @param id
	 * @return retorna um TRUE se deletar 
	 */
	public boolean delete(Long id) {
		
		try {
			Car findByIdCar = findByIdCar(id);
			
			if(findByIdCar != null) {
				Carro productDelete = createObjectCarro(findByIdCar);
				
				productDelete.setId(id);
				carroRepository.delete(productDelete);
				
				return true;
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Erro ao fazer a busca pelo ID: " + id);
		}
		
		return false;
	}
	
	
	/**
	 * Método que cria um carro do Tipo Carro a partir de um CarroDTO
	 * @param car
	 * @return retorna um carro do tipo Carro
	 */
	private Carro createObjectCarro(Car car) {
		return new Carro(car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), ("ativo".equals(car.getStatus()) ? StatusEnum.ATIVO : StatusEnum.INATIVO));
	}


}

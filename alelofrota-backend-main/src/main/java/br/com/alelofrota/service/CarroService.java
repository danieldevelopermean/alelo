package br.com.alelofrota.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alelofrota.controller.CarroController;
import br.com.alelofrota.domain.Carro;
import br.com.alelofrota.domain.dto.CarroDTO;
import br.com.alelofrota.enuns.StatusEnum;
import br.com.alelofrota.repositories.CarroRepository;

@Service
public class CarroService {
	
	private static final Logger LOGGER = Logger.getLogger(CarroController.class.getName());
	
	@Autowired
	private CarroRepository carroRepository;
	
	/**
	 * Método que lista todos os carros
	 * @return retorna uma lista de carros do tipo CarroDTO
	*/
	public List<CarroDTO> list(){
		List<Carro> cars = carroRepository.findAll();
		List<CarroDTO> carsDto = new ArrayList<CarroDTO>();
		
		for (Carro car : cars) {
			CarroDTO cartDTO = new CarroDTO(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), (car.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
			carsDto.add(cartDTO);
		}
		
		return carsDto;
	}

	/**
	 * Método que faz busca pela placa do veículo
	 * @param placa
	 * @return
	 */
	public List<CarroDTO> busaPorPlaca(String placa) {

		List<Carro> cars = carroRepository.findByPlaca(placa);
		List<CarroDTO> carsDto = new ArrayList<CarroDTO>();

		for (Carro car : cars) {
			CarroDTO cartDTO = new CarroDTO(car.getId(), car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), (car.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
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
	public CarroDTO findByIdCar(Long id) {
		Optional<Carro> obj = carroRepository.findById(id);
		CarroDTO carDTO = new CarroDTO(obj.get().getId(), obj.get().getPlaca(), obj.get().getModelo(), obj.get().getMarca(), obj.get().getCor(), (obj.get().getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
		
		return carDTO;
	}
	
	
	/**
	 * Método que cria um carro 
	 * @param car
	 * @return retorna carroDTO do carro criado
	*/
	public CarroDTO create(CarroDTO car) {
		Carro carSave = carroRepository.save(createObjectCarro(car));
		return new CarroDTO(carSave.getId(), carSave.getPlaca(), carSave.getModelo(), carSave.getMarca(), carSave.getCor(), (carSave.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
	}
	
	
	/**
	 * Método que atualiza o carro a partir do ID
	 * @param car
	 * @param id
	 * @return retorna carroDTO do carro atualizado
	*/
	public CarroDTO update(CarroDTO car, Long id) {
		
		try {
			CarroDTO findByIdCar = findByIdCar(id);
			
			if(findByIdCar != null) {
				Carro carUpdate = createObjectCarro(car);
				
				carUpdate.setId(id);
				carUpdate = carroRepository.save(carUpdate);
				return new CarroDTO(carUpdate.getId(), carUpdate.getPlaca(), carUpdate.getModelo(), carUpdate.getMarca(), carUpdate.getCor(), (carUpdate.getStatus() == StatusEnum.ATIVO) ? "Ativo" : "Inativo");
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
			CarroDTO findByIdCar = findByIdCar(id);
			
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
	private Carro createObjectCarro(CarroDTO car) {
		return new Carro(car.getPlaca(), car.getModelo(), car.getMarca(), car.getCor(), ("ativo".equals(car.getStatus()) ? StatusEnum.ATIVO : StatusEnum.INATIVO));
	}


}

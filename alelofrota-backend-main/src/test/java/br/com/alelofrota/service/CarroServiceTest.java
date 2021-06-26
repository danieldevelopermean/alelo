package br.com.alelofrota.service;

import br.com.alelofrota.domain.Carro;
import br.com.alelofrota.domain.dto.CarroDTO;
import br.com.alelofrota.repositories.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CarroServiceTest {

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarroService carroService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate(){
        when(carroRepository.save(new Carro())).thenReturn(new Carro());
        CarroDTO car = carroService.create(new CarroDTO());
        assertNotNull(car, "");
    }

    @Test
    public void testList(){
        when(carroRepository.findAll()).thenReturn(new ArrayList<Carro>());
        List<CarroDTO> list = carroService.list();
        assertNotNull(list, "");
    }

    @Test
    public void testFindById(){
        when(carroRepository.findById(1L)).thenReturn(Optional.of(new Carro()));
        CarroDTO byIdCar = carroService.findByIdCar(1L);
        assertNotNull(byIdCar, "");
    }

    private CarroDTO createObject(){
        return new CarroDTO(null, "DDD9874", "Fusca", "VW", "Branco", "Ativo");
    }
}

package br.com.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.test.domain.CarEntity;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<CarEntity, Long>{

    List<CarEntity> findByPlaca(String placa);

    CarEntity findByPlacaContaining(String placa);
}

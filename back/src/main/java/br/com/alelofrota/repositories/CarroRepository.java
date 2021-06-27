package br.com.alelofrota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alelofrota.domain.Carro;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

    List<Carro> findByPlaca(String placa);

    Carro findByPlacaContaining(String placa);
}

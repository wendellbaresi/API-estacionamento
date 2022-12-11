package com.wendell.estacionamento.repository;

import com.wendell.estacionamento.model.VagaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<VagaModel, Integer> {

    public boolean existsByPlacaCarro(String placa);

    public boolean existsByVaga(String vaga);
}

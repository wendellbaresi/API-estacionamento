package com.wendell.estacionamento.services;

import com.wendell.estacionamento.model.VagaModel;
import com.wendell.estacionamento.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public List<VagaModel> findAll(){

        return vagaRepository.findAll();
    }

    public VagaModel save(VagaModel vagaModel){

        return vagaRepository.save(vagaModel);
    }

    public boolean existiVaga(String vaga){
        return vagaRepository.existsByVaga(vaga);
    }

    public boolean existiPlacaCarro(String placa){
        return vagaRepository.existsByPlacaCarro(placa);
    }

    public Optional<VagaModel> findById(Integer id){
        return vagaRepository.findById(id);
    }

    public void deletarById(Integer id){
        vagaRepository.deleteById(id);
    }



}

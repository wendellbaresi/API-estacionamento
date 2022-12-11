package com.wendell.estacionamento.controller;

import com.wendell.estacionamento.dto.VagaDto;
import com.wendell.estacionamento.model.VagaModel;
import com.wendell.estacionamento.services.VagaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/estacionamento")
public class VagaController {

    @Autowired
    private VagaService vagaService;


    // Listar Vagas
    @GetMapping
    public List<VagaModel> getAllVagas(){

        return vagaService.findAll();
    }

    // Salvar Vagas
    @PostMapping                                           // Valid - Validar as aplicações
    public ResponseEntity<Object> salvarVaga (@RequestBody @Valid VagaDto vagadto){

        if(vagaService.existiVaga(vagadto.getVaga())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vaga está em uso!");
        }

        if (vagaService.existiPlacaCarro(vagadto.getPlacaCarro())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Placa já cadastrada!");
        }

        var vagaModel = new VagaModel();
        BeanUtils.copyProperties(vagadto, vagaModel);

        //Adicionamento de Data
        vagaModel.setData(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(vagaService.save(vagaModel));
    }


    // Listar uma Vaga
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getVaga( @PathVariable(value = "id") Integer id){
        Optional<VagaModel> vagaOptional = vagaService.findById(id);

        if(vagaOptional.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");

        } else {

            return ResponseEntity.status(HttpStatus.OK).body(vagaOptional.get());
        }

    }


    // Deletar vaga
    @DeleteMapping(value = "/{id}")
   public ResponseEntity<Object> deletarVaga (@PathVariable(value = "id") Integer id){
        Optional<VagaModel> vagaOptional = vagaService.findById(id);

        if (vagaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");
        } else {
            vagaService.deletarById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com sucesso!");
        }
   }


   // atualizar vaga
   @PutMapping(value = "/{id}")
   public ResponseEntity<Object> alterarVaga (@PathVariable(value = "id") Integer id, @RequestBody @Valid VagaDto vagaDto){
        Optional<VagaModel> vagaOptional = vagaService.findById(id);

        if (vagaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");
        }else {
            var vagaModel = new VagaModel();

            vagaModel.setUsuario(vagaDto.getUsuario());
            vagaModel.setMarcaCarro(vagaDto.getMarcaCarro());
            vagaModel.setModeloCarro(vagaDto.getModeloCarro());
            vagaModel.setPlacaCarro(vagaDto.getPlacaCarro());
            vagaModel.setVaga(vagaDto.getVaga());

            return ResponseEntity.status(HttpStatus.CREATED).body(vagaService.save(vagaModel));
        }
   }




}

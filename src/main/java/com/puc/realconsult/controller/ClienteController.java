package com.puc.realconsult.controller;

import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteApiService;

    @GetMapping
    public ResponseEntity<List<ClienteModel>> listarTodos(){
        List<ClienteModel> clientes = clienteApiService.listar();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscarporid/{id}")
    public ResponseEntity<ClienteModel> buscarPorId(@PathVariable("id") long id){
        Optional<ClienteModel> cliente = clienteApiService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetMapping("/buscarporcnpj/{cnpj}")
    public ResponseEntity<ClienteModel> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
        Optional<ClienteModel> cliente = clienteApiService.buscarPorCnpj(cnpj);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetMapping("/buscarpornome")
    public ResponseEntity<List<ClienteModel>> buscarPorNome(@RequestParam("nome") String nome){
        List<ClienteModel> clientes = clienteApiService.listarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

}

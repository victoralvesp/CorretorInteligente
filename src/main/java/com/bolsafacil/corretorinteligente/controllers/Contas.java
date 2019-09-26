package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.dtos.ContaDto;
import com.bolsafacil.corretorinteligente.services.ContasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contas
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class Contas {
    @Autowired
    ContasService service;

    @GetMapping(
        value="/contas",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> lista() {
        try {
            var contas = service.listar();
            return ok(contas);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @PostMapping(
        value="/contas/",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody ContaDto contaDto) {
        try {
            var contaConvertida = contaDto.converterParaModelo();
            var contaSalva = service.inserir(contaConvertida);
            return ok(contaSalva);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    
}
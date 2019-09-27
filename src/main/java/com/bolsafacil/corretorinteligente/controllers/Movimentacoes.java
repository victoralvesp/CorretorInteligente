package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.vms.MovimentacoesVM;
import com.bolsafacil.corretorinteligente.services.MovimentosDeContaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Movimentacoes
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class Movimentacoes {
    @Autowired
    MovimentosDeContaService service;
    

    @GetMapping(
        value="/movimentacoes",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> lista() {
        try {
            var movimentacoes = service.listar();
            var movimentacoesVM = MovimentacoesVM.converterParaViewModel(movimentacoes);
            return ok(movimentacoesVM);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

}
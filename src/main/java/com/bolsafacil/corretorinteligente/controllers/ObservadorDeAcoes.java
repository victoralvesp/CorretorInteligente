package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.dtos.AcaoObservadaDto;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.repositorios.ContasRepository;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class ObservadorDeAcoes {
    @Autowired
    RegrasDeNegociacaoService service;

    @Autowired
    MonitoramentosRepository repoMonitoramentos; 

    @Autowired
    ContasRepository repoContas;

    
    @PostMapping(
        value="/monitoramentos/",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody AcaoObservadaDto acaoObservadaDto) {
        try {
            var acaoConvertida = acaoObservadaDto.converterParaModelo();
            var movimentacoesDeConta = service.aplicarRegrasDeNegociacao(acaoConvertida);
            var contasMovimentadas = movimentacoesDeConta.stream().map(mov -> mov.getContaMovimentada());
            contasMovimentadas.forEach(conta -> conta.registrarMovimentacoes(movimentacoesDeConta.toArray(MovimentacaoDeConta[]::new)));
            repoContas.salvar(contasMovimentadas.toArray(Conta[]::new));
            return ok(movimentacoesDeConta);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

}
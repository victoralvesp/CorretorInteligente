package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.dtos.AcaoObservadaDto;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.services.ContasService;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@ComponentScan
public class ObservadorDeAcoes {
    @Autowired
    RegrasDeNegociacaoService service;

    @Autowired
    ContasService serviceContas;
    
    @PostMapping(
        value="/acoes/",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody AcaoObservadaDto acaoObservadaDto) {
        try {
            var acaoConvertida = acaoObservadaDto.converterParaModelo();
            var movimentacoesDeConta = service.aplicarRegrasDeNegociacao(acaoConvertida);
            var contasMovimentadas = movimentacoesDeConta.stream()
                                                        .map(mov -> mov.getContaMovimentada())
                                                        .toArray(ContaPessoal[]::new);

            for(ContaPessoal c : contasMovimentadas) {
                c.registrarMovimentacoes(movimentacoesDeConta.toArray(MovimentacaoDeConta[]::new));
            }
            
            var movimentacoesSalvas = serviceContas.salvar(contasMovimentadas);
            return ok(movimentacoesSalvas);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

}
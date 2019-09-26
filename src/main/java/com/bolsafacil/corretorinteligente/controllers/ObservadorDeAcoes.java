package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.dtos.AcaoObservadaDto;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.services.AcoesService;
import com.bolsafacil.corretorinteligente.services.ContasService;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;
import com.bolsafacil.corretorinteligente.services.MovimentosDeContaService;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;
import com.bolsafacil.corretorinteligente.services.implementacoes.RegrasDeNegociacaoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@ComponentScan
@Transactional
public class ObservadorDeAcoes {
    @Autowired
    ContasService serviceContas;
    @Autowired
    AcoesService serviceAcoes;
    @Autowired
    MovimentosDeContaService serviceMovimentos;
    @Autowired
    MonitoramentosService serviceMonitoramentos;
    
    @PostMapping(
        value="/acoes/",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody AcaoObservadaDto acaoObservadaDto) {
        try {
            var serviceRegras = new RegrasDeNegociacaoServiceImpl(serviceMonitoramentos);
            var acaoConvertida = acaoObservadaDto.converterParaModelo();
            var movimentacoesDeConta = serviceRegras.aplicarRegrasDeNegociacao(acaoConvertida);
            var contasMovimentadas = movimentacoesDeConta.stream()
                                                        .map(mov -> mov.getContaMovimentada())
                                                        .toArray(ContaPessoal[]::new);

            for(ContaPessoal c : contasMovimentadas) {
                c.registrarMovimentacoes(movimentacoesDeConta.toArray(MovimentacaoDeConta[]::new));
            }
            var acaoSalva = serviceAcoes.salvar(acaoConvertida);
            serviceMovimentos.salvar(movimentacoesDeConta, acaoSalva);
            var contasSalvas = serviceContas.salvar(contasMovimentadas);
            return ok(contasSalvas);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

}
package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bolsafacil.corretorinteligente.controllers.dtos.AcaoObservadaDto;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;
import com.bolsafacil.corretorinteligente.services.AcoesService;
import com.bolsafacil.corretorinteligente.services.ContasService;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;
import com.bolsafacil.corretorinteligente.services.MovimentosDeContaService;
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

    @PostMapping(value = "/acoes/", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody AcaoObservadaDto acaoObservadaDto) {
        try {
            var serviceRegras = new RegrasDeNegociacaoServiceImpl(serviceMonitoramentos);
            var acaoConvertida = acaoObservadaDto.converterParaModelo();
            var movimentacoesDeConta = serviceRegras.aplicarRegrasDeNegociacao(acaoConvertida);
            
            for (MovimentacaoDeConta mov : movimentacoesDeConta) {
                var conta = mov.getContaMovimentada();
                conta.registrarMovimentacoes(mov);
            }
            var contasMovimentadas = movimentacoesDeConta.stream()
                                            .map(mov -> mov.getContaMovimentada())
                                            .distinct()
                                            .toArray(ContaPessoal[]::new);


            var acaoSalva = serviceAcoes.salvar(acaoConvertida);
            serviceMovimentos.salvar(movimentacoesDeConta, acaoSalva);
            serviceContas.salvar(contasMovimentadas);
            var movimentacoesVM = ConverterParaViewModel(movimentacoesDeConta);
            return ok(movimentacoesVM);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    private MovimentacoesVM ConverterParaViewModel(
            Collection<? extends MovimentacaoDeConta> movimentacoesDeConta) {
        var movimentacoesVM = movimentacoesDeConta.stream().map(mov -> new MovimentacaoVM(mov)).collect(Collectors.toList());
        return new MovimentacoesVM(movimentacoesVM);
    }
    private class MovimentacoesVM {
        public Collection<MovimentacaoVM> movimentacoes;
        public MovimentacoesVM(Collection<MovimentacaoVM> movimentacoesVM) {
            this.movimentacoes = movimentacoesVM;
        }
    }

    private class MovimentacaoVM {

        public TipoMovimentacao tipo;
        public String data;
        public String quantidadeDeAcoes;
        public String valorMovimentado;
        public String idConta;
        public String empresa;

        public MovimentacaoVM(MovimentacaoDeConta mov) {
            tipo = mov.getTipoMovimentacao();
            idConta = String.valueOf(mov.getContaMovimentada().getId());
            valorMovimentado = mov.getValorMovimentado().toString();
            quantidadeDeAcoes = mov.getQuantidadeDeAcoesMovimentada().toString();
            empresa = mov.getEmpresaDaAcaoMovimentada();
            data = mov.getDataMovimentacao().toString();
        }

    }

}
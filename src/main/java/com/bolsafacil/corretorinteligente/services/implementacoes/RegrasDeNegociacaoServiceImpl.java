package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeNegociacao;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeVenda;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;

/**
 * RegrasDeNegociacaoService
 */
public class RegrasDeNegociacaoServiceImpl implements RegrasDeNegociacaoService {

    private final MonitoramentosRepository repoMonitoramentos;

    public RegrasDeNegociacaoServiceImpl(MonitoramentosRepository repoMonitoramentos) {
        this.repoMonitoramentos = repoMonitoramentos;
    }

    Collection<RegraDeNegociacao> regrasMonitoradas;

    @Override
    public Collection<RegraDeNegociacao> getRegrasDeNegociacao() {
        if (regrasMonitoradas == null)
            regrasMonitoradas = definirRegrasMonitoradas();
        return regrasMonitoradas;
    }

    private Collection<RegraDeNegociacao> definirRegrasMonitoradas() {
        var monitoramentos = repoMonitoramentos.listar();
        return monitoramentos.stream()
                 .flatMap(mnt -> criarRegra(mnt))
                 .collect(toList());
    }

    private Stream<RegraDeNegociacao> criarRegra(Monitoramento monitoramento) {
        if(monitoramento == null) {
            return Stream.of();
        }
        
        var contaDoMonitoramento = monitoramento.getConta();

        var regrasCriadas = new ArrayList<RegraDeNegociacao>();

        if (monitoramento.getPrecoCompra().compareTo(BigDecimal.ZERO) > 0) {
            var saldoDisponivelNaConta = contaDoMonitoramento.getSaldoAtual();
            regrasCriadas.add(new RegraDeCompra(monitoramento, saldoDisponivelNaConta));
        }
        // if (monitoramento.getPrecoVenda().compareTo(BigDecimal.ZERO) > 0) {
        //     regrasCriadas.add(new RegraDeVenda(monitoramentoDaRegra, quantidadeDeAcoesDisponivel))
        // }
        

        return regrasCriadas.stream();
    }

    @Override
    public Collection<? super MovimentacaoDeConta> aplicarRegrasDeNegociacao(AcaoObservada acaoObservada) {
        if (regrasMonitoradas == null) {
            return null;
        }
        return regrasMonitoradas.stream()
                        .map(regra -> regra.aplicarRegra(acaoObservada))
                        .collect(toList());
    }

    
}
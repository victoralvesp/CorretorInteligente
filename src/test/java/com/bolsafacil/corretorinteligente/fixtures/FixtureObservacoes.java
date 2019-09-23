package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;

import org.mockito.Mockito;

/**
 * FixtureDatabase
 */
public class FixtureObservacoes {
    Set<ObservacaoDeAcoes> observacoesSalvas;
    Random idGenerator = new Random();
    
    public FixtureObservacoes() {
    }
    public ObservacaoDeAcoes criarNovaObservacao() {
        long id = idGenerator.nextLong();
        return this.criarNovaObservacao(id);
    }

    public ObservacaoDeAcoes criarNovaObservacao(long id) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        var acaoMonitorada = new AcaoObservada("Intel", precoCompra, precoVenda);
        var dataDoMonitoramento = LocalDateTime.now();
        Set<AcaoObservada> acoesDoMonitoramento = new HashSet<AcaoObservada>(Arrays.asList(acaoMonitorada));
        
        return new ObservacaoDeAcoes(acoesDoMonitoramento, dataDoMonitoramento, id);
    }
    
    public void preencherObservacoes(Set<ObservacaoDeAcoes> monitoramentos) {
        observacoesSalvas = monitoramentos;
    }

    public void preencherObservacoes(int qtde) {
        var monitoramentos = new ObservacaoDeAcoes[] {};
        this.preencherObservacoes(qtde, monitoramentos);
    }

    public void preencherObservacoes(int qtde, ObservacaoDeAcoes... seed) {
        var observacoes = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtde; i++) {
            var monitoramento = criarNovaObservacao();
            observacoes.add(monitoramento);
        }
        observacoesSalvas = new HashSet<>(observacoes);
    }

    public ObservacoesDeAcaoRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(ObservacoesDeAcaoRepository.class);
        when(repoMock.salvar(any(ObservacaoDeAcoes.class))).then(returnsFirstArg());
        when(repoMock.listar()).thenAnswer(i -> getMonitoramentosSalvos());
        when(repoMock.buscar(any(Long.class))).thenAnswer(an -> {
            var id = an.getArguments()[0];
            for(ObservacaoDeAcoes m : getMonitoramentosSalvos()) {
                if(m.getId().equals(id))
                    return m;
            }
            return null;
        });

        return repoMock;
    }

    public Set<ObservacaoDeAcoes> getMonitoramentosSalvos() {
        return observacoesSalvas;
    }
}
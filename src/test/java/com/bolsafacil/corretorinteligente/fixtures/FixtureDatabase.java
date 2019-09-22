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
import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;

import org.mockito.Mockito;

/**
 * FixtureDatabase
 */
public class FixtureDatabase {
    Set<ObservacoesDeAcao> monitoramentosSalvos;
    Random idGenerator = new Random();
    
    public FixtureDatabase() {
    }
    public ObservacoesDeAcao criarNovoMonitoramento() {
        long id = idGenerator.nextLong();
        return this.criarNovoMonitoramento(id);
    }

    public ObservacoesDeAcao criarNovoMonitoramento(long id) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        var acaoMonitorada = new AcaoObservada("Intel", precoCompra, precoVenda);
        var dataDoMonitoramento = LocalDateTime.now();
        Set<AcaoObservada> acoesDoMonitoramento = new HashSet<AcaoObservada>(Arrays.asList(acaoMonitorada));
        
        return new ObservacoesDeAcao(acoesDoMonitoramento, dataDoMonitoramento, id);
    }
    
    public void preencherMonitoramentos(Set<ObservacoesDeAcao> monitoramentos) {
        monitoramentosSalvos = monitoramentos;
    }

    public void preencherMonitoramentos(int qtde) {
        var monitoramentos = new ObservacoesDeAcao[] {};
        this.preencherMonitoramentos(qtde, monitoramentos);
    }

    public void preencherMonitoramentos(int qtde, ObservacoesDeAcao... seed) {
        var monitoramentos = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtde; i++) {
            var monitoramento = criarNovoMonitoramento();
            monitoramentos.add(monitoramento);
        }
        monitoramentosSalvos = new HashSet<>(monitoramentos);
    }

    public ObservacoesDeAcaoRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(ObservacoesDeAcaoRepository.class);
        when(repoMock.salvar(any(ObservacoesDeAcao.class))).then(returnsFirstArg());
        when(repoMock.listar()).thenAnswer(i -> getMonitoramentosSalvos());
        when(repoMock.buscar(any(Long.class))).thenAnswer(an -> {
            var id = an.getArguments()[0];
            for(ObservacoesDeAcao m : getMonitoramentosSalvos()) {
                if(m.getId().equals(id))
                    return m;
            }
            return null;
        });

        return repoMock;
    }

    public Set<ObservacoesDeAcao> getMonitoramentosSalvos() {
        return monitoramentosSalvos;
    }
}
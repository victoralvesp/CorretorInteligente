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


import com.bolsafacil.corretorinteligente.domain.AcaoDoMonitoramento;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;

import org.mockito.Mockito;

/**
 * FixtureDatabase
 */
public class FixtureDatabase {
    Set<Monitoramento> monitoramentosSalvos;
    Random idGenerator = new Random();
    
    public FixtureDatabase() {
    }
    public Monitoramento criarNovoMonitoramento() {
        long id = idGenerator.nextLong();
        return this.criarNovoMonitoramento(id);
    }

    public Monitoramento criarNovoMonitoramento(long id) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        var acaoMonitorada = new AcaoDoMonitoramento("Intel", precoCompra, precoVenda);
        var dataDoMonitoramento = LocalDateTime.now();
        Set<AcaoDoMonitoramento> acoesDoMonitoramento = new HashSet<AcaoDoMonitoramento>(Arrays.asList(acaoMonitorada));
        
        return new Monitoramento(acoesDoMonitoramento, dataDoMonitoramento, id);
    }
    
    public void preencherMonitoramentos(Set<Monitoramento> monitoramentos) {
        monitoramentosSalvos = monitoramentos;
    }

    public void preencherMonitoramentos(int qtde) {
        var monitoramentos = new Monitoramento[] {};
        this.preencherMonitoramentos(qtde, monitoramentos);
    }

    public void preencherMonitoramentos(int qtde, Monitoramento... seed) {
        var monitoramentos = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtde; i++) {
            var monitoramento = criarNovoMonitoramento();
            monitoramentos.add(monitoramento);
        }
        monitoramentosSalvos = new HashSet<>(monitoramentos);
    }

    public MonitoramentosRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(MonitoramentosRepository.class);
        when(repoMock.salvar(any(Monitoramento.class))).then(returnsFirstArg());
        when(repoMock.listar()).thenAnswer(i -> getMonitoramentosSalvos());
        when(repoMock.buscar(any(Long.class))).thenAnswer(an -> {
            var id = an.getArguments()[0];
            for(Monitoramento m : getMonitoramentosSalvos()) {
                if(m.getId().equals(id))
                    return m;
            }
            return null;
        });

        return repoMock;
    }

    public Set<Monitoramento> getMonitoramentosSalvos() {
        return monitoramentosSalvos;
    }
}
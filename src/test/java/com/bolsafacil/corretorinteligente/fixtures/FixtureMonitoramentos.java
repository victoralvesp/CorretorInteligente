package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.mockito.AdditionalAnswers.*;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;

import org.mockito.Mockito;

/**
 * FixtureMonitoramentos
 */
public class FixtureMonitoramentos {

    Set<Monitoramento> monitoramentosSalvos;
    private final Random idGenerator = new Random();

    public Monitoramento criarNovoMonitoramento() {
        String empresa = "Intel" + idGenerator.nextInt(10000);
        return this.criarNovoMonitoramento(empresa);
    }

    public Monitoramento criarNovoMonitoramento(String empresa) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        
        return new Monitoramento(empresa, precoCompra, precoVenda);
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

        when(repoMock.buscar(any(String.class))).thenAnswer(an -> {
            var empresa = an.getArguments()[0];
            for(Monitoramento m : getMonitoramentosSalvos()) {
                if(m.getEmpresa().equals(empresa)) {
                    return m;
                }
            }
            return null;
        });

        return repoMock;
    }
 
    public Set<Monitoramento> getMonitoramentosSalvos() {
        return monitoramentosSalvos;
    }
}
package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;

import org.mockito.Mockito;

/**
 * FixtureMonitoramentos
 */
public class FixtureMonitoramentos {

    Set<Monitoramento> monitoramentosSalvos;
    private final Random idGenerator = new Random();

    public Monitoramento criarNovoMonitoramento() {
        int id = idGenerator.nextInt(10000);
        String empresa = "Intel" + id;
        return this.criarNovoMonitoramento(empresa);
    }

    public Monitoramento criarNovoMonitoramento(String empresa) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        
        return this.criarNovoMonitoramento(empresa, precoCompra, precoVenda);
    }

    public Monitoramento criarNovoMonitoramento(BigDecimal precoCompra, BigDecimal precoVenda) {
        var empresa = "Intel";
        
        return this.criarNovoMonitoramento(empresa, precoCompra, precoVenda);
    }

    public Monitoramento criarNovoMonitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
        var monitoramento = new Monitoramento(empresa, precoCompra, precoVenda);
        var conta = new ContaPessoal(BigDecimal.ZERO);
        monitoramento.setConta(conta);
        return monitoramento;
    }

    public void preencherMonitoramentos(int qtde) {
        var monitoramentos = new Monitoramento[] {};
        this.preencherMonitoramentos(qtde, monitoramentos);
    }
    public void preencherMonitoramentos(Monitoramento... seed) {
        this.preencherMonitoramentos(0, seed);
    }

    public void preencherMonitoramentos(int qtdeDeEntradas, Monitoramento... seed) {
        var monitoramentos = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtdeDeEntradas; i++) {
            var monitoramento = criarNovoMonitoramento();
            monitoramentos.add(monitoramento);
        }
        monitoramentosSalvos = new HashSet<>(monitoramentos);
    }

    

    public MonitoramentosRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(MonitoramentosRepository.class);
        when(repoMock.incluir(any(Monitoramento.class))).thenAnswer(monArg -> adicionarMonitoramento(monArg.getArguments()[0]));

        when(repoMock.listar()).thenAnswer(i -> getMonitoramentosSalvos());

        when(repoMock.alterarParaExcluido(any(Monitoramento.class))).thenAnswer(an -> {
            var monitoramento = (Monitoramento) an.getArguments()[0];

            monitoramento.setExcluido(true);
            return monitoramento;
        });
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
 
    private Monitoramento adicionarMonitoramento(Object object) {
        var monitoramentoAAdicionar = (Monitoramento) object;

        monitoramentosSalvos.add(monitoramentoAAdicionar);

        return monitoramentoAAdicionar;
    }

    public Set<Monitoramento> getMonitoramentosSalvos() {
        return monitoramentosSalvos;
    }
}
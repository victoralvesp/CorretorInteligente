package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.repositorios.AcoesObservadasRepository;

import org.mockito.Mockito;

/**
 * FixtureDatabase
 */
public class FixtureObservacoes {
    Set<AcaoObservada> observacoesSalvas;
    Random idGenerator = new Random();
    
    public FixtureObservacoes() {
    }
    public AcaoObservada criarNovaObservacao() {
        long id = idGenerator.nextLong();
        return this.criarNovaObservacao(id);
    }

    public AcaoObservada criarNovaObservacao(long id) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        var dataDoMonitoramento = LocalDateTime.now();
        
        return new AcaoObservada("Intel", precoCompra, precoVenda);
    }
    
    public void preencherObservacoes(Set<AcaoObservada> observacoes) {
        observacoesSalvas = observacoes;
    }

    public void preencherObservacoes(int qtde) {
        var monitoramentos = new AcaoObservada[] {};
        this.preencherObservacoes(qtde, monitoramentos);
    }

    public void preencherObservacoes(int qtde, AcaoObservada... seed) {
        var observacoes = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtde; i++) {
            var monitoramento = criarNovaObservacao();
            observacoes.add(monitoramento);
        }
        observacoesSalvas = new HashSet<>(observacoes);
    }

    public AcoesObservadasRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(AcoesObservadasRepository.class);
        when(repoMock.save(any(AcaoObservada.class))).then(returnsFirstArg());
        when(repoMock.findAll()).thenAnswer(i -> getObservacoesSalvas());
        when(repoMock.findById(any(Long.class))).thenAnswer(an -> {
            var id = an.getArguments()[0];
            for(AcaoObservada m : getObservacoesSalvas()) {
                if(id != null && id.equals(m.getId()))
                    return m;
            }
            return null;
        });

        return repoMock;
    }

    public Set<AcaoObservada> getObservacoesSalvas() {
        return observacoesSalvas;
    }
}
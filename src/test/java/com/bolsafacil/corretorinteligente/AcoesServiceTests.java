package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.fixtures.FixtureObservacoes;
import com.bolsafacil.corretorinteligente.services.AcoesService;
import com.bolsafacil.corretorinteligente.services.implementacoes.AcoesServiceImpl;

import org.junit.Before;
import org.junit.Test;

/**
 * MonitorServiceTests
 */
public class AcoesServiceTests {

    private AcoesService monitorService;
    private FixtureObservacoes fixtureDB;

    @Before
    public void setup() {
        fixtureDB = new FixtureObservacoes();
        var monitoramentosRepoMock = fixtureDB.criarMockMonitoramentosRepository();
        monitorService = new AcoesServiceImpl(monitoramentosRepoMock);
    }

    @Test
    public void observacaoDeveSerSalvaComDataDeRegistroAtual() {
        // Arrange
        var monitoramento = fixtureDB.criarNovaObservacao();
        var dataAtual = LocalDateTime.now();
        // Act
        var monitoramentoRegistrado = monitorService.registrarObservacaoDeAcoes(monitoramento);
        // Assert
        assertTrue("message", monitoramentoRegistrado.getData().compareTo(dataAtual) >= 0);
    }

    @Test
    public void deveListarObservacoesSalvas() {
        // Arrange
        int qtde = 10;
        fixtureDB.preencherObservacoes(qtde);

        // Act
        var monitoramentos = monitorService.listarObservacoesRealizadas();

        // Assert
        assertTrue("Os monitoramentos não foram listados corretamente", monitoramentos.size() == qtde);
    }
}
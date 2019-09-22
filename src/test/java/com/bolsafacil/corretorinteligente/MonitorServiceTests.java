package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.fixtures.FixtureDatabase;
import com.bolsafacil.corretorinteligente.services.MonitorService;
import com.bolsafacil.corretorinteligente.services.MonitorServiceImpl;

import org.junit.Before;
import org.junit.Test;

import javassist.NotFoundException;

/**
 * MonitorServiceTests
 */
public class MonitorServiceTests {

    private MonitorService monitorService;
    private FixtureDatabase fixtureDB;

    @Before
    public void setup() {
        fixtureDB = new FixtureDatabase();
        var monitoramentosRepoMock = fixtureDB.criarMockMonitoramentosRepository();
        monitorService = new MonitorServiceImpl(monitoramentosRepoMock);
    }

    @Test
    public void monitoramentoDeveSerSalvoComDataDeRegistroAtual() {
        // Arrange
        var monitoramento = fixtureDB.criarNovoMonitoramento();
        var dataAtual = LocalDateTime.now();
        // Act
        var monitoramentoRegistrado = monitorService.registrarMonitoramento(monitoramento);
        // Assert
        assertTrue("message", monitoramentoRegistrado.getDataRegistro().compareTo(dataAtual) >= 0);
    }

    @Test
    public void deveListarMonitoramentosSalvos() {
        // Arrange
        int qtde = 10;
        fixtureDB.preencherMonitoramentos(qtde);

        // Act
        var monitoramentos = monitorService.listarMonitoramentos();

        // Assert
        assertTrue("Os monitoramentos não foram listados corretamente", monitoramentos.size() == qtde);
    }

    @Test
    public void deveObterApenasMonitoramentoBuscado() throws NotFoundException {
        //Arrange
        int qtde = 10;
        var monitoramentoAlvo = fixtureDB.criarNovoMonitoramento();
        var idAlvo = monitoramentoAlvo.getId();
        fixtureDB.preencherMonitoramentos(qtde, monitoramentoAlvo);

        //Act
        var monitoramentoBuscado = monitorService.buscarMonitoramento(idAlvo);

        //Assert
        assertTrue("Monitoramento buscado não é o mesmo do Alvo", monitoramentoAlvo.equals(monitoramentoBuscado));
    }

}
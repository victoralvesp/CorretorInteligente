package com.bolsafacil.corretorinteligente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.fixtures.FixtureMonitoramentos;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;
import com.bolsafacil.corretorinteligente.services.implementacoes.MonitoramentosServiceImpl;

import org.junit.Before;
import org.junit.Test;

import javassist.NotFoundException;

/**
 * MonitoramentosServiceTests
 */
public class MonitoramentosServiceTests {

    private FixtureMonitoramentos fixtureDB;
    private MonitoramentosService monitoramentosService;

    @Before
    public void setup() throws NotFoundException {
        fixtureDB = new FixtureMonitoramentos();
        var repositorio = fixtureDB.criarMockMonitoramentosRepository();
        monitoramentosService = new MonitoramentosServiceImpl(repositorio);
    }

    @Test
    public void deveListarMonitoramentosSalvos() {
        //Arrange 
        var qtdeMonitoramentosSalvos = 5;
        fixtureDB.preencherMonitoramentos(qtdeMonitoramentosSalvos);

        //Act
        var monitoramentos = monitoramentosService.listarMonitoramentos();

        //Assert
        assertTrue(monitoramentos != null && monitoramentos.size() > 0);
    }
    @Test
    public void naoDeveManterDoisMonitoramentosDaMesmaEmpresa() {
        //Arrange
        var empresa = "Intel";
        var monitoramentoJaSalvo = fixtureDB.criarNovoMonitoramento(empresa);
        int qtdeDeEntradas = 10;
        fixtureDB.preencherMonitoramentos(qtdeDeEntradas, monitoramentoJaSalvo);
        var monitoramentoAInserir = fixtureDB.criarNovoMonitoramento(empresa);

        //Act
        monitoramentosService.salvarMonitoramento(monitoramentoAInserir);
        var monitoramentosSalvos = monitoramentosService.listarMonitoramentos();
        //Assert
        assertThat(monitoramentosSalvos).extracting(Monitoramento::getEmpresa).containsOnlyOnce(empresa);
    }
    @Test
    public void deveListarApenasMonitoramentosNaoExcluidos() {
        //Arrange 
        var qtdeMonitoramentosSalvos = 5;
        var monitoramentoExcluido = fixtureDB.criarNovoMonitoramento();
        monitoramentoExcluido.setExcluido(true);
        fixtureDB.preencherMonitoramentos(qtdeMonitoramentosSalvos, monitoramentoExcluido);

        //Act
        var monitoramentos = monitoramentosService.listarMonitoramentos();

        //Assert
        assertThat(monitoramentos).extracting(Monitoramento::getExcluido).allMatch(prop -> prop == false); 
    }    
}   
package com.bolsafacil.corretorinteligente;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.fixtures.FixtureMonitoramentos;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import org.junit.Before;
import org.junit.Test;

/**
 * MonitoramentosServiceTests
 */
public class MonitoramentosServiceTests {

    
    private FixtureMonitoramentos fixtureDB;
    private MonitoramentosService monitoramentosService;

    @Before
    public void setup() {
        fixtureDB = new FixtureMonitoramentos();
        var repositorio = fixtureDB.criarMockMonitoramentosRepository();
        monitoramentosService = new MonitoramentosService(){
        
            @Override
            public Collection<Monitoramento> listarMonitoramentos() {
                return null;
            }
        
            @Override
            public Monitoramento incluirMonitoramento(Monitoramento monitoramento) {
                return null;
            }
        
            @Override
            public boolean excluirMonitoramento(String empresa) {
                return false;
            }
        
            @Override
            public Monitoramento buscarMonitoramento(String empresa) {
                return null;
            }
        };
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
    public void deveListarApenasMonitoramentosNaoExcluidos() {
        //Arrange 
        var qtdeMonitoramentosSalvos = 5;
        fixtureDB.preencherMonitoramentos(qtdeMonitoramentosSalvos);

        //Act
        var monitoramentos = monitoramentosService.listarMonitoramentos();

        //Assert
        assertThat(monitoramentos, hasItem(hasProperty("excluido", is(true)))); 
    }

    // public void deve
}
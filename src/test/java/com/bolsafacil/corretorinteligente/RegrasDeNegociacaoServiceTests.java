package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertTrue;

import com.bolsafacil.corretorinteligente.fixtures.FixtureDatabase;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;

import org.junit.Test;

/**
 * RegrasDeNegociacaoServiceTests
 */
public class RegrasDeNegociacaoServiceTests {

    //@Test
    public void deveAplicarRegraDeCompraAMonitoramentoAcimaDaRegra() {
        //Arrange
        RegrasDeNegociacaoService regrasDeNegociacaoService = null;
        var fixtureDB = new FixtureDatabase();
        var monitoramento = fixtureDB.criarNovoMonitoramento();
        
        //Act
        var movimentacoesDeAcoesGeradas = regrasDeNegociacaoService.aplicarRegrasDeNegociacao(monitoramento);

        //Assert
        assertTrue("A aplicacao de regras de compra não gerou resultado", movimentacoesDeAcoesGeradas != null && movimentacoesDeAcoesGeradas.size() > 0);
    }
}
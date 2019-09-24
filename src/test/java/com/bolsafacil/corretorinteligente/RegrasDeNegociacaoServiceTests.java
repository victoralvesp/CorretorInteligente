package com.bolsafacil.corretorinteligente;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeVenda;
import com.bolsafacil.corretorinteligente.fixtures.FixtureMonitoramentos;
import com.bolsafacil.corretorinteligente.fixtures.FixtureObservacoes;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;
import com.bolsafacil.corretorinteligente.services.implementacoes.RegrasDeNegociacaoServiceImpl;

import org.assertj.core.api.Condition;
import org.junit.Test;

/**
 * RegrasDeNegociacaoServiceTests
 */
public class RegrasDeNegociacaoServiceTests {

    // @Test
    public void deveAplicarRegraDeCompraAMonitoramentoAcimaDaRegra() {
        // Arrange
        RegrasDeNegociacaoService regrasDeNegociacaoService = null;
        var fixtureDB = new FixtureObservacoes();
        var observacao = fixtureDB.criarNovaObservacao();

        // Act
        // var movimentacoesDeAcoesGeradas = regrasDeNegociacaoService.aplicarRegrasDeNegociacao(observacao);

        // Assert
        // assertTrue("A aplicacao de regras de compra não gerou resultado",
        //         movimentacoesDeAcoesGeradas != null && movimentacoesDeAcoesGeradas.size() > 0);
    }

    @Test
    public void deveCriarUmaRegraDeCompraParaUmMonitoramentoComPrecoDeCompraPositivoSalvo() {
        // Arrange
        var fixtureDB = new FixtureMonitoramentos();
        var precoCompra = new BigDecimal("10.00");
        var monitoramentoComCompra = fixtureDB.criarNovoMonitoramento(precoCompra, null);
        fixtureDB.preencherMonitoramentos(monitoramentoComCompra);
        var repo = fixtureDB.criarMockMonitoramentosRepository();
        var service = criarService(repo);

        //Act
        var regras = service.getRegrasDeNegociacao();

        //Assert
        var ehRegraDeCompra = new Condition<>(regra -> regra.getClass() == RegraDeCompra.class, "É regra de compra");
        assertThat(regras).extracting(regra -> regra).areExactly(1, ehRegraDeCompra);
    }
    @Test
    public void deveCriarUmaRegraDeVendaParaUmMonitoramentoComPrecoDeVendaPositivoSalvo() {
        // Arrange
        var fixtureDB = new FixtureMonitoramentos();
        var precoVenda = new BigDecimal("10.00");
        var monitoramentoComVenda = fixtureDB.criarNovoMonitoramento(null, precoVenda);
        fixtureDB.preencherMonitoramentos(monitoramentoComVenda);
        var repo = fixtureDB.criarMockMonitoramentosRepository();
        var service = criarService(repo);

        //Act
        var regras = service.getRegrasDeNegociacao();

        //Assert
        var ehRegraDeVenda = new Condition<>(regra -> regra.getClass() == RegraDeVenda.class, "É regra de compra");
        assertThat(regras).extracting(regra -> regra).areExactly(1, ehRegraDeVenda);
    }

    private RegrasDeNegociacaoService criarService(MonitoramentosRepository repo) {
        return new RegrasDeNegociacaoServiceImpl(repo);
    }
}
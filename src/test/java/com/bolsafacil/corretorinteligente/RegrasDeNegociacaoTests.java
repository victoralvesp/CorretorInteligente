package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.fixtures.FixtureRegras;

import org.junit.Test;

/**
 * RegrasDeNegociacaoTests
 */
public class RegrasDeNegociacaoTests {

    @Test
    public void regraDeCompraDeveGerarMovimentacaoParaAcaoDeMonitoramentoComValorApropriado() {
        //Arrange
        String empresa = "Intel";
        var fixtureRegra = new FixtureRegras();
        var regraDeCompraExemplo = fixtureRegra.criarRegraDeCompra(empresa);
        var acaoMonitorada = criarAcaoObservada(empresa);

        // Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        assertNotNull(movimentacaoGerada);
    }

    @Test
    public void regraDeCompraDeveComprarQuantidadeConformeExemplo() {
        String empresa = "Intel";
        var fixtureRegra = new FixtureRegras();
        var regraDeCompraExemplo = fixtureRegra.criarRegraDeCompra(empresa);
        var acaoObservada = criarAcaoObservada(empresa);

        // Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoObservada);
        // Assert
        var quantidadeCompradaExemplo = new BigDecimal("1123.59");
        Number valorMovimentado = movimentacaoGerada.getQuantidadeDeAcoesMovimentada();
        var message = "Quantidade esperada: " + quantidadeCompradaExemplo + " Quantidade calculada: " + valorMovimentado;
        assertTrue(message, valorMovimentado.equals(quantidadeCompradaExemplo));
    }

    @Test
    public void regraDeCompraNaoDeveSerAplicadaParaEmpresaNaoMonitarada() {
        String empresaObservada = "Microsoft";
        String empresaMonitorada = "Intel";
        var fixtureRegra = new FixtureRegras();
        var regraDeCompraExemplo = fixtureRegra.criarRegraDeCompra(empresaMonitorada);
        var acaoMonitorada = criarAcaoObservada(empresaObservada);

        // Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        var message = "Foi gerada movimentacao para empresa errada";
        assertNull(message, movimentacaoGerada);
    }
    
    @Test
    public void naoDeveDeixarSaldoDeContaNegativo() {
        String empresaMonitorada = "Intel";
        var fixtureRegra = new FixtureRegras();
        var saldoDisponivel = "0.5";
        var regraDeCompraExemplo = fixtureRegra.criarRegraDeCompra(empresaMonitorada, saldoDisponivel);
        var acaoMonitorada = criarAcaoObservada(empresaMonitorada);

        //Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoMonitorada);

        assertTrue(movimentacaoGerada == null 
                || movimentacaoGerada.getValorMovimentado().compareTo(new BigDecimal(saldoDisponivel)) <= 0);
    }

    
    @Test
    public void regraDeVendaDeveGerarMovimentacaoParaAcaoDeMonitoramentoComValorApropriado() {
        //Arrange
        String empresa = "Intel";
        var fixtureRegra = new FixtureRegras();
        var regraDeVendaExemplo = fixtureRegra.criarRegraDeVenda(empresa);
        var acaoMonitorada = criarAcaoObservada(empresa, "8.90", "15.00");

        // Act
        var movimentacaoGerada = regraDeVendaExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        assertNotNull(movimentacaoGerada);
    }

    @Test
    public void regraDeVendaDeveVenderQuantidadeConformeExemplo() {
        String empresa = "Intel";
        var fixtureRegra = new FixtureRegras();
        var valorMonitorado = new BigDecimal("8.00");
        var regraDeVendaExemplo = fixtureRegra.criarRegraDeVenda(empresa, valorMonitorado);
        var acaoMonitorada = criarAcaoObservada(empresa);

        // Act
        var movimentacaoGerada = regraDeVendaExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        var valorTotalVendidoExemplo = new BigDecimal("10674.10");
        Number valorMovimentado = movimentacaoGerada.getValorMovimentado();
        var message = "Quantidade esperada: " + valorTotalVendidoExemplo + " Quantidade calculada: " + valorMovimentado;
        assertTrue(message, valorMovimentado.equals(valorTotalVendidoExemplo));

    }

    @Test
    public void regraDeVendaNaoDeveSerAplicadaParaEmpresaNaoMonitarada() {
        String empresaObservada = "Microsoft";
        String empresaMonitorada = "Intel";
        var fixtureRegra = new FixtureRegras();
        var regraDeVendaExemplo = fixtureRegra.criarRegraDeVenda(empresaMonitorada);
        var acaoMonitorada = criarAcaoObservada(empresaObservada);

        // Act
        var movimentacaoGerada = regraDeVendaExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        var message = "Foi gerada movimentacao para empresa errada";
        assertNull(message, movimentacaoGerada);

    }
    
    private AcaoObservada criarAcaoObservada(String empresa) {
        var precoCompra = "8.90";
        return criarAcaoObservada(empresa, precoCompra);
    }

    private AcaoObservada criarAcaoObservada(String empresa, String precoCompra) {
        var precoVenda = "9.50";
        return criarAcaoObservada(empresa, precoCompra, precoVenda);
    }
    
    private AcaoObservada criarAcaoObservada(String empresa, String precoCompra, String precoVenda) {
        var precoCompraObservado = new BigDecimal(precoCompra);
        var precoVendaObservado = new BigDecimal(precoVenda);
        var acaoMonitorada = new AcaoObservada(empresa, precoCompraObservado, precoVendaObservado);
        return acaoMonitorada;
    }
    
}
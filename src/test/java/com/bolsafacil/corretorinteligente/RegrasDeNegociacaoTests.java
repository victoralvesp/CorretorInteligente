package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeNegociacao;

import org.junit.Test;

/**
 * RegrasDeNegociacaoTests
 */
public class RegrasDeNegociacaoTests {

    @Test
    public void regraDeCompraDeveGerarMovimentacaoParaAcaoDeMonitoramentoComValorApropriado() {
        //Arrange
        String empresa = "Intel";
        var regraDeCompraExemplo = criarRegraDeCompra(empresa);
        var acaoMonitorada = criarAcaoObservada(empresa);

        // Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        assertNotNull(movimentacaoGerada);
    }

    private AcaoObservada criarAcaoObservada(String empresa) {
        var precoCompraObservado = new BigDecimal("8.90");
        var precoVendaObservado = new BigDecimal("9.50");
        var acaoMonitorada = new AcaoObservada(empresa, precoCompraObservado, precoVendaObservado);
        return acaoMonitorada;
    }

    private Monitoramento criarMonitoramento(String empresa) {
        var precoCompra = new BigDecimal("10.00");
        var precoVenda = new BigDecimal("11.00");
        var monitoramentoDoExemplo = new Monitoramento(empresa, precoCompra, precoVenda);
        return monitoramentoDoExemplo;
    }

    @Test
    public void regraDeCompraDeveComprarQuantidadeConformeExemplo() {
        String empresa = "Intel";
        var regraDeCompraExemplo = criarRegraDeCompra(empresa);
        var acaoMonitorada = criarAcaoObservada(empresa);

        // Act
        var movimentacaoGerada = regraDeCompraExemplo.aplicarRegra(acaoMonitorada);
        // Assert
        var quantidadeCompradaExemplo = new BigDecimal("1123.59");
        Number valorMovimentado = movimentacaoGerada.getValorMovimentado();
        var message = "Quantidade esperada: " + quantidadeCompradaExemplo + " Quantidade calculada: " + valorMovimentado;
        assertTrue(message, valorMovimentado.equals(quantidadeCompradaExemplo));

    }
    

    private RegraDeNegociacao criarRegraDeCompra(String empresa) {
        var monitoramentoDoExemplo = criarMonitoramento(empresa);
        var saldoDisponivel = new BigDecimal("10000.00");
        var regraDeCompraExemplo = new RegraDeCompra(monitoramentoDoExemplo, saldoDisponivel);
        return regraDeCompraExemplo;
    }
}
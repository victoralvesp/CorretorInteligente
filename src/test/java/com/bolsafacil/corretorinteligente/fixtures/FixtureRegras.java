package com.bolsafacil.corretorinteligente.fixtures;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeNegociacao;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeVenda;

/**
 * FixtureRegras
 */
public class FixtureRegras {
    public FixtureRegras() {
    }
    public RegraDeNegociacao criarRegraDeVenda(String empresa) {
        var monitoramentoDoExemplo = criarMonitoramento(empresa);
        var saldoDaAcaoMonitoradaDisponivel = new BigDecimal("1123.59");
        var regraDeVendaExemplo = new RegraDeVenda(monitoramentoDoExemplo, saldoDaAcaoMonitoradaDisponivel);
        return regraDeVendaExemplo;
    }

    public RegraDeNegociacao criarRegraDeCompra(String empresa) {
        var monitoramentoDoExemplo = criarMonitoramento(empresa);
        var saldoDisponivel = new BigDecimal("10000.00");
        var regraDeCompraExemplo = new RegraDeCompra(monitoramentoDoExemplo, saldoDisponivel);
        return regraDeCompraExemplo;
    }


    private Monitoramento criarMonitoramento(String empresa) {
        var precoCompra = new BigDecimal("10.00");
        var precoVenda = new BigDecimal("11.00");
        var monitoramentoDoExemplo = new Monitoramento(empresa, precoCompra, precoVenda);
        return monitoramentoDoExemplo;
    }
}
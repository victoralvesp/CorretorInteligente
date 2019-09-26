package com.bolsafacil.corretorinteligente.fixtures;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcaoImpl;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
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
        return criarRegraDeVenda(empresa, new BigDecimal("11.00"));
    }

    public RegraDeNegociacao criarRegraDeVenda(String empresa, BigDecimal valorMonitoradoVenda) {
        var saldoDaAcaoMonitoradaDisponivel = new BigDecimal("1123.59");
        var contaDeAcao = new ContaDeAcaoImpl(saldoDaAcaoMonitoradaDisponivel, null, empresa);
        var conta = new FixtureContas().criarContaPessoalPreenchida(contaDeAcao);
        var regraDeVendaExemplo = criarRegraDeVenda(empresa, valorMonitoradoVenda, saldoDaAcaoMonitoradaDisponivel, conta);
        return regraDeVendaExemplo;
    }

    public RegraDeNegociacao criarRegraDeVenda(String empresa, BigDecimal valorMonitoradoVenda, BigDecimal saldoDaAcaoMonitoradaDisponivel, ContaPessoal conta) {
        var monitoramentoDoExemplo = criarMonitoramento(empresa, new BigDecimal("10.00"), valorMonitoradoVenda);
        monitoramentoDoExemplo.setConta(conta);
        var regraDeVendaExemplo = new RegraDeVenda(monitoramentoDoExemplo, saldoDaAcaoMonitoradaDisponivel);
        return regraDeVendaExemplo;
    }

    public RegraDeNegociacao criarRegraDeVenda(String empresa, BigDecimal valorMonitoradoVenda, ContaPessoal conta) {
        var regraDeVendaExemplo = criarRegraDeVenda(empresa, valorMonitoradoVenda, BigDecimal.ZERO, conta);
        return regraDeVendaExemplo;
    }

    public RegraDeNegociacao criarRegraDeCompra(String empresa) {
        var saldoDisponivel = "10000.00";
        return criarRegraDeCompra(empresa, saldoDisponivel);
    }
    
    public RegraDeNegociacao criarRegraDeCompra(String empresa, String saldo) {
        var saldoDisponivel = new BigDecimal(saldo);
        var monitoramentoDoExemplo = criarMonitoramento(empresa);
        var regraDeCompraExemplo = new RegraDeCompra(monitoramentoDoExemplo, saldoDisponivel);
        return regraDeCompraExemplo;
    }

    private Monitoramento criarMonitoramento(String empresa) {
        var precoCompra = new BigDecimal("10.00");
        var precoVenda = new BigDecimal("11.00");
        return criarMonitoramento(empresa, precoCompra, precoVenda);
    }

    private Monitoramento criarMonitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
        var monitoramentoDoExemplo = new Monitoramento(empresa, precoCompra, precoVenda);
        return monitoramentoDoExemplo;
    }
}
package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.Conta;

/**
 * MovimentacaoDeVendaDeAcoes
 */
public class MovimentacaoDeVendaDeAcoes implements MovimentacaoDeConta {

    public MovimentacaoDeVendaDeAcoes(BigDecimal valorTotalVendido, LocalDateTime dataDaVenda,
            BigDecimal quantidadeDeAcoes, String empresa, Conta conta) {
        this.valorTotalVendido = valorTotalVendido;
        this.dataDaVenda = dataDaVenda;
        empresaDaAcaoMovimentada = empresa;
        quantidadeDeAcoesMovimentadas = quantidadeDeAcoes;
        this.conta = conta;
    }

    private final BigDecimal valorTotalVendido;
    private final LocalDateTime dataDaVenda;
    private final String empresaDaAcaoMovimentada;
    private final BigDecimal quantidadeDeAcoesMovimentadas;
    private Conta conta;

    @Override
    public BigDecimal getValorMovimentado() {
        return valorTotalVendido;
    }

    @Override
    public LocalDateTime getDataMovimentacao() {
        return dataDaVenda;
    }

    @Override
    public BigDecimal getQuantidadeDeAcoesMovimentada() {
        return quantidadeDeAcoesMovimentadas;
    }

    @Override
    public String getEmpresaDaAcaoMovimentada() {
        return empresaDaAcaoMovimentada;
    }

    @Override
    public TipoMovimentacao getTipoMovimentacao() {
        return TipoMovimentacao.VENDA;
    }

    @Override
    public Conta getContaMovimentada() {
        return conta;
    }

}
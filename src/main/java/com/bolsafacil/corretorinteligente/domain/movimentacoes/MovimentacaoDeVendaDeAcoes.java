package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * MovimentacaoDeVendaDeAcoes
 */
public class MovimentacaoDeVendaDeAcoes implements MovimentacaoDeConta{

    public MovimentacaoDeVendaDeAcoes(BigDecimal valorTotalVendido, LocalDateTime dataDaVenda) {
        this.valorTotalVendido = valorTotalVendido;
        this.dataDaVenda = dataDaVenda;
    }

    private final BigDecimal valorTotalVendido;
    private final LocalDateTime dataDaVenda;

    @Override
    public Number getValorMovimentado() {
        return valorTotalVendido;
    }

    @Override
    public LocalDateTime getDataMovimentacao() {
        return dataDaVenda;
    }

}
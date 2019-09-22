package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * MovimentacaoDeCompraDeAcoes
 */
public class MovimentacaoDeCompraDeAcoes implements MovimentacaoDeConta {

    public MovimentacaoDeCompraDeAcoes(BigDecimal quantidadeDeAcoesCompradas, LocalDateTime dataDaCompra) {
        this.quantidadeDeAcoesCompradas = quantidadeDeAcoesCompradas;
        this.dataDaCompra = dataDaCompra;
    }

    private final BigDecimal quantidadeDeAcoesCompradas;
    private final LocalDateTime dataDaCompra;

    @Override
    public Number getValorMovimentado() {
        return quantidadeDeAcoesCompradas;
    }

    @Override
    public LocalDateTime getDataMovimentacao() {
        return dataDaCompra;
    }

    
}
package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * MovimentacaoDeCompraDeAcoes
 */
public class MovimentacaoDeCompraDeAcoes implements MovimentacaoDeConta {

    private final BigDecimal valorComprado;
    private final String empresaDasAcoes;
    private final BigDecimal quantidadeDeAcoesCompradas;
    private final LocalDateTime dataDaCompra;

    public MovimentacaoDeCompraDeAcoes(BigDecimal valorTotalMovimentado, LocalDateTime dataDaCompra,
            BigDecimal quantidadeDeAcoesCompradas, String empresa) {
        this.quantidadeDeAcoesCompradas = quantidadeDeAcoesCompradas;
        this.dataDaCompra = dataDaCompra;
        this.valorComprado = valorTotalMovimentado;
        this.empresaDasAcoes = empresa;
    }

    @Override
    public BigDecimal getValorMovimentado() {
        return valorComprado;
    }

    @Override
    public LocalDateTime getDataMovimentacao() {
        return dataDaCompra;
    }

    @Override
    public BigDecimal getQuantidadeDeAcoesMovimentada() {
        return quantidadeDeAcoesCompradas;
    }

    @Override
    public String getEmpresaDaAcaoMovimentada() {
        return empresaDasAcoes;
    }

    @Override
    public TipoMovimentacao getTipoMovimentacao() {
        return TipoMovimentacao.COMPRA;
    }

    
}
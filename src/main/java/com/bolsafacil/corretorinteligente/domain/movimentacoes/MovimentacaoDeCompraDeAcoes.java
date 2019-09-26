package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * MovimentacaoDeCompraDeAcoes
 */
public class MovimentacaoDeCompraDeAcoes implements MovimentacaoDeConta {

    private final BigDecimal valorMovimentado;
    private final String empresaDasAcoes;
    private final BigDecimal quantidadeDeAcoesCompradas;
    private final LocalDateTime dataDaCompra;
    private ContaPessoal conta;
    private long id;
    private AcaoObservada acaoObservada;

    public MovimentacaoDeCompraDeAcoes(BigDecimal valorTotalMovimentado, LocalDateTime dataDaCompra,
            BigDecimal quantidadeDeAcoesCompradas, String empresa, ContaPessoal conta) {
        this.quantidadeDeAcoesCompradas = quantidadeDeAcoesCompradas;
        this.dataDaCompra = dataDaCompra;
        this.valorMovimentado = valorTotalMovimentado;
        this.empresaDasAcoes = empresa;
        this.conta = conta;
    }

    @Override
    public BigDecimal getValorMovimentado() {
        return valorMovimentado;
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

    @Override
    public ContaPessoal getContaMovimentada() {
        return conta;
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    @Override
    public AcaoObservada getAcaoObservada() {
        return acaoObservada;
    }

    @Override
    public void setAcaoObservada(AcaoObservada acao) {
        acaoObservada = acao;
    }

    
}
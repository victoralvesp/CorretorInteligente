package com.bolsafacil.corretorinteligente.domain.movimentacoes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * MovimentacaoDeVendaDeAcoes
 */
public class MovimentacaoDeVendaDeAcoes implements MovimentacaoDeConta {

    public MovimentacaoDeVendaDeAcoes(BigDecimal valorTotalVendido, LocalDateTime dataDaVenda,
            BigDecimal quantidadeDeAcoes, String empresa, ContaPessoal conta) {
        this.valorTotalMovimentado = valorTotalVendido;
        this.dataDaVenda = dataDaVenda;
        empresaDaAcaoMovimentada = empresa;
        quantidadeDeAcoesMovimentadas = quantidadeDeAcoes;
        this.conta = conta;
    }

    private final BigDecimal valorTotalMovimentado;
    private final LocalDateTime dataDaVenda;
    private final String empresaDaAcaoMovimentada;
    private final BigDecimal quantidadeDeAcoesMovimentadas;
    private ContaPessoal conta;
    private long id;
    private AcaoObservada acaoObservada;

    @Override
    public BigDecimal getValorMovimentado() {
        return valorTotalMovimentado;
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
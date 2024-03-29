package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * ContaDeAcaoImpl
 */
public class ContaDeAcaoImpl extends ContaBase implements ContaDeAcao {
    
    String empresaDaAcao;
    ContaPessoal conta;

    /**
     * @return the conta
     */
    public ContaPessoal getConta() {
        return conta;
    }
    /**
     * @param conta the conta to set
     */
    public void setConta(ContaPessoal conta) {
        this.conta = conta;
    }
    public ContaDeAcaoImpl(BigDecimal saldoInicial, String empresaDaAcao) {
        super(saldoInicial);
        this.empresaDaAcao = empresaDaAcao;
        
    }

    public ContaDeAcaoImpl(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva, String empresaDaAcao) {
        super(saldoInicial, dataUltimaAtualizacaoSalva, (long) 0);
        this.empresaDaAcao = empresaDaAcao;
    }

    public ContaDeAcaoImpl(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva, String empresaDaAcao, long id) {
        super(saldoInicial, dataUltimaAtualizacaoSalva, id);
        this.empresaDaAcao = empresaDaAcao;
    }

    /**
     * @return the empresaDaAcao
     */
    @Override
    public String getEmpresaDaAcao() {
        return empresaDaAcao;
    }

    

    @Override
    protected boolean movimentacaoPodeAlterarEstaConta(MovimentacaoDeConta movimentacao) {
        return movimentacao.getEmpresaDaAcaoMovimentada().equals(this.empresaDaAcao);
    }

    @Override
    protected void registrarMovimentacaoDeVenda(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.subtract(movimentacao.getQuantidadeDeAcoesMovimentada().abs());
    }

    @Override
    protected void registrarMovimentacaoDeCompra(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.add(movimentacao.getQuantidadeDeAcoesMovimentada());
    }

    
}
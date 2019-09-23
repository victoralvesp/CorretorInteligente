package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * ContaPessoal
 */
public class ContaPessoal extends ContaBase {

    String email;

    public ContaPessoal(BigDecimal saldoInicial) {
        super(saldoInicial);
    }

    public ContaPessoal(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva) {
        super(saldoInicial, dataUltimaAtualizacaoSalva);
        
    }


    @Override
    protected void registrarMovimentacaoDeVenda(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.add(movimentacao.getValorMovimentado());
    }
    @Override
    protected void registrarMovimentacaoDeCompra(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.subtract(movimentacao.getValorMovimentado());
    }

    /**
     * @return email da conta
     */
    public String getEmail() {
        return email;
    }

    @Override
    protected boolean movimentacaoAlteraEstaConta(MovimentacaoDeConta movimentacao) {
        return true;
    }
}
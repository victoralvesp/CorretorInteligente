package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * ContaImpl
 */
public abstract class ContaBase implements Conta {

    LocalDateTime dataUltimaAtualizacaoSalva;

    BigDecimal saldoDisponivelInicial;

    protected BigDecimal saldoMovimentacoes;
    protected List<MovimentacaoDeConta> movimentacoesRegistradas = new ArrayList<MovimentacaoDeConta>();

    public ContaBase(BigDecimal saldoInicial) {
        this(saldoInicial, LocalDateTime.MIN);
    }

    public ContaBase(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva) {
        saldoDisponivelInicial = saldoInicial;
        saldoMovimentacoes = new BigDecimal("0");
        this.dataUltimaAtualizacaoSalva = dataUltimaAtualizacaoSalva;
    }

    @Override
    public BigDecimal getSaldoAtual() {
        return saldoDisponivelInicial.add(saldoMovimentacoes);
    }

    @Override
    public void registrarMovimentacoes(MovimentacaoDeConta... movimentacoes) {
        if (movimentacoes == null)
            return;

        for (MovimentacaoDeConta movimentacao : movimentacoes) {
            if(movimentacaoAlteraEstaConta(movimentacao)) {

                var tipoMovimentacao = movimentacao.getTipoMovimentacao();
                if(tipoMovimentacao == null) {
                    registrarMovimentacaoSemAlterarSaldo(movimentacao);
                }
                else {
                    switch (tipoMovimentacao) {
                        case COMPRA:
                        registrarMovimentacaoDeCompra(movimentacao);
                        break;
                        case VENDA:
                        registrarMovimentacaoDeVenda(movimentacao);
                        default:
                        registrarMovimentacaoSemAlterarSaldo(movimentacao);
                        break;
                    }
                }            
            }
        }
    }

    protected abstract boolean movimentacaoAlteraEstaConta(MovimentacaoDeConta movimentacao);
        

    private void registrarMovimentacaoSemAlterarSaldo(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
    }

    protected abstract void registrarMovimentacaoDeVenda(MovimentacaoDeConta movimentacao);

    protected abstract void registrarMovimentacaoDeCompra(MovimentacaoDeConta movimentacao);

    @Override
    public LocalDateTime getDataUltimaAtualizacao() {
        return movimentacoesRegistradas.stream()
                                       .map(MovimentacaoDeConta::getDataMovimentacao)
                                       .max(Comparator.naturalOrder())
                                       .orElse(this.dataUltimaAtualizacaoSalva);
    }


    
}
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
public class ContaImpl implements Conta {

    LocalDateTime dataUltimaAtualizacaoSalva;

    BigDecimal saldoDisponivelInicial;

    private BigDecimal saldoMovimentacoes;
    private List<MovimentacaoDeConta> movimentacoesRegistradas = new ArrayList<MovimentacaoDeConta>();

    public ContaImpl(BigDecimal saldoInicial) {
        this(saldoInicial, LocalDateTime.MIN);
    }

    public ContaImpl(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva) {
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

            var tipoMovimentacao = movimentacao.getTipoMovimentacao();
            if(tipoMovimentacao == null) {
                registrarMovimentacaoSemAlterarSaldo(movimentacao);
            }
            else {
                switch (tipoMovimentacao) {
                    case COMPRA:
                    subtrairSaldoMovimentacao(movimentacao);
                    break;
                    case VENDA:
                    adicionarSaldoMovimentacao(movimentacao);
                    default:
                    registrarMovimentacaoSemAlterarSaldo(movimentacao);
                    break;
                }
            }            
        }
    }

    private void registrarMovimentacaoSemAlterarSaldo(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
    }

    private void adicionarSaldoMovimentacao(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.add(movimentacao.getValorMovimentado());
    }

    private void subtrairSaldoMovimentacao(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.subtract(movimentacao.getValorMovimentado());
    }

    @Override
    public LocalDateTime getDataUltimaAtualizacao() {
        return movimentacoesRegistradas.stream()
                                       .map(MovimentacaoDeConta::getDataMovimentacao)
                                       .max(Comparator.naturalOrder())
                                       .orElse(this.dataUltimaAtualizacaoSalva);
    }


    
}
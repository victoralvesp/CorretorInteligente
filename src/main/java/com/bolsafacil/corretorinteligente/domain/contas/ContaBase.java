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
    long id;
    protected LocalDateTime dataUltimaAtualizacaoSalva;

    BigDecimal saldoDisponivelInicial;

    protected BigDecimal saldoMovimentacoes;
    protected List<MovimentacaoDeConta> movimentacoesRegistradas = new ArrayList<MovimentacaoDeConta>();

    public ContaBase(BigDecimal saldoInicial) {
        this(saldoInicial, LocalDateTime.MIN, (long) 0);
    }

    public ContaBase(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva, Long id) {
        saldoDisponivelInicial = saldoInicial;
        saldoMovimentacoes = new BigDecimal("0");
        this.dataUltimaAtualizacaoSalva = dataUltimaAtualizacaoSalva;
        this.id = id;
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
            if(movimentacaoPodeAlterarEstaConta(movimentacao)) {

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

    protected abstract boolean movimentacaoPodeAlterarEstaConta(MovimentacaoDeConta movimentacao);
        

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


    /**
     * @param dataUltimaAtualizacaoSalva the dataUltimaAtualizacaoSalva to set
     */
    public void setDataUltimaAtualizacaoSalva(LocalDateTime dataUltimaAtualizacaoSalva) {
        this.dataUltimaAtualizacaoSalva = dataUltimaAtualizacaoSalva;
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }
}
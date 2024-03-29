package com.bolsafacil.corretorinteligente.domain.regrasdenegociacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;

/**
 * RegraDeCompra
 */
public class RegraDeCompra implements RegraDeNegociacao {

    /**
     *
     */

    private static final RoundingMode modoArredondamento = RoundingMode.DOWN;
    /**
     *
     */

    private final BigDecimal saldoDisponivel;
    private final Monitoramento monitoramentoDaRegra;

    public RegraDeCompra(Monitoramento monitoramentoDaRegra, BigDecimal saldoDisponivel) {
        this.monitoramentoDaRegra = monitoramentoDaRegra;
        this.saldoDisponivel = saldoDisponivel;
    }

    @Override
    public MovimentacaoDeConta aplicarRegra(AcaoObservada acaoObservada) {
        var precoCompraObservado = acaoObservada.getPrecoCompra();

        String empresaDaAcao = acaoObservada.getEmpresa();
        var empresaObservaIgualAMonitorada = monitoramentoDaRegra.getEmpresa().equals(empresaDaAcao);
        if (empresaObservaIgualAMonitorada && estaAbaixoDoDesejado(precoCompraObservado) && contaTemSaldo(precoCompraObservado)) {
            var quantidadeDeAcoesAComprar = saldoDisponivel.divide(precoCompraObservado, modoArredondamento).setScale(2, modoArredondamento);
            var dataDaCompra = DefinicoesDoServidor.getDataAtual();
            var valorTotalMovimentado = quantidadeDeAcoesAComprar.multiply(precoCompraObservado)
                                                                 .multiply(new BigDecimal(-1))
                                                                 .setScale(2, modoArredondamento);
            var movimentacaoDeCompra = new MovimentacaoDeCompraDeAcoes(valorTotalMovimentado, dataDaCompra,
                                                                            quantidadeDeAcoesAComprar, empresaDaAcao
                                                                            , getContaDoMonitoramento());

            return movimentacaoDeCompra;
        } else {
            return null;
        }

    }

    private boolean contaTemSaldo(BigDecimal precoCompraObservado) {
        var possivelQuantidadeAComprar = saldoDisponivel.divide(precoCompraObservado, modoArredondamento).setScale(2, modoArredondamento);
        return possivelQuantidadeAComprar.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean estaAbaixoDoDesejado(BigDecimal precoCompraObservado) {
        var precoDesejado = monitoramentoDaRegra.getPrecoCompra();
        return precoCompraObservado.compareTo(precoDesejado) <= 0;
    }

    private ContaPessoal getContaDoMonitoramento() {
        return monitoramentoDaRegra.getConta();
    }
    
}
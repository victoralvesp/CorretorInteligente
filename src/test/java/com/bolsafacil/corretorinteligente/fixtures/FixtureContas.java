package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeVendaDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;
import com.bolsafacil.corretorinteligente.services.ContasService;

import org.assertj.core.util.Lists;
import org.mockito.Mockito;

import javassist.NotFoundException;

/**
 * FixtureContas
 */
public class FixtureContas {

    public ContaPessoal criarContaPessoalPreenchida() {
        return this.criarContaPessoalPreenchida(BigDecimal.ZERO);
    }

    private Random rand = new Random();

    public ContaPessoal criarContaPessoalPreenchida(BigDecimal saldoInicial) {
        var randId = rand.nextInt(10000);
        var email = "bla" + randId + "@domain.com";
        var data = LocalDateTime.now();
        return criarContaPessoalPreenchida(email, saldoInicial, data);
    }

    public ContaPessoal criarContaPessoalPreenchida(ContaDeAcao... contasDeAcao) {
        var randId = rand.nextInt(10000);
        var email = "bla" + randId + "@domain.com";
        var data = LocalDateTime.now();
        return criarContaPessoalPreenchida(email, BigDecimal.ZERO, data, contasDeAcao);
    }

    public ContaPessoal criarContaPessoalPreenchida(String email, BigDecimal saldo, LocalDateTime data,
            ContaDeAcao... contas) {
        return new ContaPessoal(email, saldo, data, Lists.list(contas));
    }

    public MovimentacaoDeConta criarVendaComValorDe(String val) {
        var empresa = "Intel";
        return criarVendaComValorDe(val, empresa);
    }

    public MovimentacaoDeConta criarVendaComValorDe(String val, String empresa) {
        var valorVendido = new BigDecimal(val);
        var conta = criarContaPessoalPreenchida();
        return criarVendaComValorDe(empresa, valorVendido, conta);
    }

    public MovimentacaoDeConta criarVendaComValorDe(String val, ContaPessoal conta) {
        var valorVendido = new BigDecimal(val);
        var empresa = "Intel";
        return criarVendaComValorDe(empresa, valorVendido, conta);
    }

    private MovimentacaoDeConta criarVendaComValorDe(String empresa, BigDecimal valorVendido, ContaPessoal conta) {
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        var movimentacao = new MovimentacaoDeVendaDeAcoes(valorVendido, data, quantidade, empresa, conta);
        return movimentacao;
    }

    public MovimentacaoDeConta criarCompraComValorDe(String val) {
        var empresa = "Intel";
        return criarCompraComValorDe(val, empresa);
    }

    public MovimentacaoDeConta criarCompraComValorDe(String val, ContaPessoal conta) {
        var empresa = "Intel";
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        return criarCompraComValorDe(val, empresa, conta, data, quantidade);
    }

    public MovimentacaoDeConta criarCompraComValorDe(String val, String empresa) {
        var conta = criarContaPessoalPreenchida();
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        return criarCompraComValorDe(val, empresa, conta, data, quantidade);
    }

    private MovimentacaoDeConta criarCompraComValorDe(String val, String empresa, ContaPessoal conta,
            LocalDateTime data, BigDecimal quantidade) {
        var valorVendido = new BigDecimal(val);
        var movimentacao = new MovimentacaoDeCompraDeAcoes(valorVendido, data, quantidade, empresa, conta);
        return movimentacao;
    }

    public MovimentacaoDeConta criarMovimentacaoGenerica(String valor, LocalDateTime dataDaMovimentacao) {
        return new MovimentacaoDeConta() {

            @Override
            public BigDecimal getValorMovimentado() {
                return new BigDecimal(valor);
            }

            @Override
            public TipoMovimentacao getTipoMovimentacao() {
                return null;
            }

            @Override
            public BigDecimal getQuantidadeDeAcoesMovimentada() {
                return new BigDecimal("10");
            }

            @Override
            public String getEmpresaDaAcaoMovimentada() {
                return "Intel";
            }

            @Override
            public LocalDateTime getDataMovimentacao() {
                return dataDaMovimentacao;
            }

            @Override
            public ContaPessoal getContaMovimentada() {
                return null;
            }

            @Override
            public long getId() {
                return 0;
            }

            @Override
            public AcaoObservada getAcaoObservada() {
                return null;
            }

            @Override
            public void setAcaoObservada(AcaoObservada acao) {

            }
        };
    }

    public ContasService criarMockContasService() throws NotFoundException {
        var contasService = Mockito.mock(ContasService.class);
        
        when(contasService.buscar(anyLong())).thenAnswer(an -> criarContaPessoalPreenchida()); 
        return contasService;
	}

}
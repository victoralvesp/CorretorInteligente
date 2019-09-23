package com.bolsafacil.corretorinteligente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.Conta;
import com.bolsafacil.corretorinteligente.domain.ContaImpl;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeVendaDeAcoes;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ContasServiceTests
 */
public class ContasTests {

    @Test
    public void deveSubtrairValorDeMovimentacaoDeCompraASaldoDisponivel() {
        //Arrange
        Conta conta = new ContaImpl(new BigDecimal("10000.00")); 
        var movimentacao1 = criarCompraComValorDe("1500.00");
        var movimentacao2 = criarCompraComValorDe("2271.00");
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6229.00");
    }

    public void deveSomarValorDeMovimentacaoDeVendaASaldoDisponivel() {
        //Arrange
        Conta conta = new ContaImpl(new BigDecimal("2500.00"));
        var movimentacao1 = criarVendaComValorDe("1500.00");
        var movimentacao2 = criarVendaComValorDe("2271.00");
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6271.00");
    }
    

    private MovimentacaoDeConta criarVendaComValorDe(String val) {
        var valorVendido = new BigDecimal(val);
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        var empresa = "Intel";
        var movimentacao = new MovimentacaoDeVendaDeAcoes(valorVendido, data, quantidade, empresa);
        return movimentacao;
    }

    private MovimentacaoDeConta criarCompraComValorDe(String val) {
        var valorVendido = new BigDecimal(val);
        var data = LocalDateTime.now();
        var quantidade = new BigDecimal("10");
        var empresa = "Intel";
        var movimentacao = new MovimentacaoDeCompraDeAcoes(valorVendido, data, quantidade, empresa);
        return movimentacao;
    }

}
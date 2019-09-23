package com.bolsafacil.corretorinteligente;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.fixtures.FixtureContas;

import org.junit.Test;

/**
 * ContasServiceTests
 */
public class ContasTests {

    @Test
    public void deveSubtrairValorDeMovimentacaoDeCompraASaldoDisponivel() {
        //Arrange
        Conta conta = new ContaPessoal(new BigDecimal("10000.00")); 
        var fixtureDB = new FixtureContas();
        var movimentacao1 = fixtureDB.criarCompraComValorDe("1500.00");
        var movimentacao2 = fixtureDB.criarCompraComValorDe("2271.00");
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6229.00");
    }
    @Test
    public void deveSomarValorDeMovimentacaoDeVendaASaldoDisponivel() {
        //Arrange
        Conta conta = new ContaPessoal(new BigDecimal("2500.00"));
        var fixtureDB = new FixtureContas();
        var movimentacao1 = fixtureDB.criarVendaComValorDe("1500.00");
        var movimentacao2 = fixtureDB.criarVendaComValorDe("2271.00");
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6271.00");
    }

    @Test
    public void dataDaUltimaAtualizacaoDaContaDeveSerAUltimaMovimentacaoRegistrada() {
        //Arrange
        Conta conta = new ContaPessoal(new BigDecimal("2500.00"));
        var fixtureDB = new FixtureContas();
        var agora = LocalDateTime.now();
        var hojeMaisCedo = agora.minusHours(2);
        var movimentacao1 = fixtureDB.criarMovimentacaoGenerica("1500.00", hojeMaisCedo);
        var movimentacao2 = fixtureDB.criarMovimentacaoGenerica("2271.00", agora);
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        var dataUltimaAtualizacaoDaConta = conta.getDataUltimaAtualizacao();
        assertThat(dataUltimaAtualizacaoDaConta).isEqualToIgnoringNanos(agora);
    }
    

}
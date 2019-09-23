package com.bolsafacil.corretorinteligente;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcaoImpl;
import com.bolsafacil.corretorinteligente.fixtures.FixtureContas;

import org.junit.Test;

/**
 * ContasDeAcoesTests
 */
public class ContasDeAcoesTests {

    @Test
    public void deveSomarValorDeMovimentacaoDeVendaASaldoDisponivel() {
        // Arrange
        String empresa = "Intel";
        ContaDeAcao conta = new ContaDeAcaoImpl(new BigDecimal("2500.00"), empresa); 
        var fixtureDB = new FixtureContas();
        var movimentacao1 = fixtureDB.criarCompraComValorDe("1500.00", empresa);
        var movimentacao2 = fixtureDB.criarCompraComValorDe("2271.00", empresa);
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6271.00");
    }
    @Test
    public void deveSubtrairValorDeMovimentacaoDeCompraASaldoDisponivel() {
        //Arrange
        String empresa = "Intel";
        ContaDeAcao conta = new ContaDeAcaoImpl(new BigDecimal("10000.00"), empresa);
        var fixtureDB = new FixtureContas();
        var movimentacao1 = fixtureDB.criarVendaComValorDe("1500.00", empresa);
        var movimentacao2 = fixtureDB.criarVendaComValorDe("2271.00", empresa);
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("6229.00");
    }
    @Test
    public void naoDeveAlterarSaldoComMovimentacoesDeAcoesDeOutrasEmpresas() {
        //Arrange
        String empresa = "Intel";
        String outraEmpresa = "letnI";
        ContaDeAcao conta = new ContaDeAcaoImpl(new BigDecimal("2500.00"), empresa);
        var fixtureDB = new FixtureContas();
        var movimentacao1 = fixtureDB.criarCompraComValorDe("1500.00", outraEmpresa);
        var movimentacao2 = fixtureDB.criarVendaComValorDe("2271.00", outraEmpresa);
        // Act
        conta.registrarMovimentacoes(movimentacao1, movimentacao2);

        // Assert
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo("2500.00");
    }

    @Test
    public void dataDaUltimaAtualizacaoDaContaDeveSerAUltimaMovimentacaoRegistrada() {
        //Arrange
        String empresa = "Intel";
        ContaDeAcao conta = new ContaDeAcaoImpl(new BigDecimal("2500.00"), empresa);
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
package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * ContaPessoal
 */
public class ContaPessoal extends ContaBase {

    String email;
    private Collection<ContaDeAcao> contasDeAcao;

    public ContaPessoal(BigDecimal saldoInicial) {
        super(saldoInicial);
        contasDeAcao = new ArrayList<ContaDeAcao>();
    }

    public ContaPessoal(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva) {
        super(saldoInicial, dataUltimaAtualizacaoSalva);
        contasDeAcao = new ArrayList<ContaDeAcao>();
    }

    public ContaPessoal(BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva,
            Collection<ContaDeAcao> contasDeAcao) {
        super(saldoInicial, dataUltimaAtualizacaoSalva);
        this.contasDeAcao = contasDeAcao;
    }

    public BigDecimal buscarQuantidadeDeAcoesDisponivel(String empresa) {
        return buscarContaDeAcaoDaEmpresa(empresa)
                .map(conta -> conta.getSaldoAtual())
                .orElse(BigDecimal.ZERO);
    }

    private Optional<ContaDeAcao> buscarContaDeAcaoDaEmpresa(String empresa) {
        return contasDeAcao.stream().filter(conta -> conta.getEmpresaDaAcao().equals(empresa))
                                .findAny();
    }

    @Override
    protected void registrarMovimentacaoDeVenda(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        registrarMovimentacaoEmContaDeAcao(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.add(movimentacao.getValorMovimentado());
    }
    @Override
    protected void registrarMovimentacaoDeCompra(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        registrarMovimentacaoEmContaDeAcao(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.subtract(movimentacao.getValorMovimentado());
    }

    private void registrarMovimentacaoEmContaDeAcao(MovimentacaoDeConta movimentacao) {
        var empresaDaAcaoMovimentada = movimentacao.getEmpresaDaAcaoMovimentada();
        var contaDaAcaoMovimentada = buscarContaDeAcaoDaEmpresa(empresaDaAcaoMovimentada);
        
        contaDaAcaoMovimentada.ifPresentOrElse(conta -> conta.registrarMovimentacoes(movimentacao),
                                               () -> adicionarNovaContaERegistrarMovimentacao(movimentacao));
    }

    private void adicionarNovaContaERegistrarMovimentacao(MovimentacaoDeConta movimentacao) {
        var empresaDaAcaoMovimentada = movimentacao.getEmpresaDaAcaoMovimentada();
        var novaConta = new ContaDeAcaoImpl(BigDecimal.ZERO, DefinicoesDoServidor.getDataAtual(), empresaDaAcaoMovimentada);
        novaConta.registrarMovimentacoes(movimentacao);
        contasDeAcao.add(novaConta);
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
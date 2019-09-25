package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * ContaPessoal
 */
@Entity
public class ContaPessoal extends ContaBase {

    @Id
    long id;

    String email;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<ContaDeAcao> contasDeAcao;

    public ContaPessoal(String email, BigDecimal saldoInicial) {
        this(email, saldoInicial, LocalDateTime.MIN, new ArrayList<ContaDeAcao>());
    }

    public ContaPessoal(String email, BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva) {
        this(email, saldoInicial, dataUltimaAtualizacaoSalva, new ArrayList<ContaDeAcao>());
    }

    public ContaPessoal(String email, BigDecimal saldoInicial, LocalDateTime dataUltimaAtualizacaoSalva, Collection<ContaDeAcao> contasDeAcao) {
        super(saldoInicial, dataUltimaAtualizacaoSalva);
        this.contasDeAcao = contasDeAcao;
        this.email = email;
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

    /**
     * @return contas de ações pertencentes ao dono da conta pessoal
     */
    public Collection<ContaDeAcao> getContasDeAcao() {
        return contasDeAcao;
    }

    @Override
    protected boolean movimentacaoPodeAlterarEstaConta(MovimentacaoDeConta movimentacao) {
        return equals(movimentacao.getContaMovimentada());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass() != this.getClass())
            return false;

        var other = (ContaPessoal) obj;
        return email.equals(other.getEmail());
    }
}
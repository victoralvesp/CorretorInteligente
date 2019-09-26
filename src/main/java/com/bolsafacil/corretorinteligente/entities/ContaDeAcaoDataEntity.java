package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcaoImpl;


/**
 * ContaDeAcaoDataEntity
 */
@Entity
@Table(name = "contas_de_acao")
public class ContaDeAcaoDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(nullable = false, name = "nome_empresa_acao")
    String empresa;

    @Column(nullable = false, name = "id_conta")
    long idConta;

    
    @Column(nullable = false, name = "data_ultima_atualizacao")
    LocalDateTime dataUltimaAtualizacao;

    @Column(nullable = false, name = "quantidade_disponivel")
    BigDecimal quantidadeDisponivel;

    public ContaDeAcao converterParaModelo() {
        ContaDeAcaoImpl contaDeAcaoImpl = new ContaDeAcaoImpl(quantidadeDisponivel, dataUltimaAtualizacao, empresa, id);
        
        return contaDeAcaoImpl;
    }

    public static ContaDeAcaoDataEntity converterDe(ContaDeAcao contaAcao, long idConta) {
        var contaEntity = new ContaDeAcaoDataEntity();
        contaEntity.dataUltimaAtualizacao = contaAcao.getDataUltimaAtualizacao();
        contaEntity.empresa = contaAcao.getEmpresaDaAcao();
        contaEntity.id = contaAcao.getId();
        contaEntity.quantidadeDisponivel = contaAcao.getSaldoAtual();
        contaEntity.idConta = idConta;

        return contaEntity;
    }

    public static ContaDeAcaoDataEntity converterDe(ContaDeAcao conta) {
        var contaEntity = new ContaDeAcaoDataEntity();
        contaEntity.dataUltimaAtualizacao = conta.getDataUltimaAtualizacao();
        contaEntity.empresa = conta.getEmpresaDaAcao();
        contaEntity.id = conta.getId();
        contaEntity.quantidadeDisponivel = conta.getSaldoAtual();

        return contaEntity;
    }
}
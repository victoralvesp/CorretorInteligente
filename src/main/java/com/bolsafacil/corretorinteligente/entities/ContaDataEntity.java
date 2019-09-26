package com.bolsafacil.corretorinteligente.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;

/**
 * ContaDataEntity
 */
@Entity
@Table(name = "contas_pessoais")
public class ContaDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false, name = "data_ultima_atualizacao")
    LocalDateTime dataUltimaAtualizacao;

    @Column(nullable = false, name = "saldo_disponivel")
    BigDecimal saldoDisponivel;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_conta")
    Set<ContaDeAcaoDataEntity> contasDeAcao;


    public ContaPessoal converterParaModelo() {
        List<ContaDeAcao> contasDeAcaoConvertidas = new ArrayList<ContaDeAcao>();
        if(contasDeAcao != null)
        {
            contasDeAcaoConvertidas = contasDeAcao.stream().map(ent -> ent.converterParaModelo())
                                                     .collect(Collectors.toList());
        }   

        return new ContaPessoal(email, saldoDisponivel, dataUltimaAtualizacao, contasDeAcaoConvertidas, id);
    }

    public static ContaDataEntity converterDe(ContaPessoal conta) {
        var contasDeAcaoMaybe = Optional.of(conta.getContasDeAcao());

        var contaEntity = new ContaDataEntity();
        contaEntity.id = conta.getId();
        contasDeAcaoMaybe.ifPresentOrElse(contas -> {
                contaEntity.contasDeAcao = contas.stream().map(contaDeAcao -> ContaDeAcaoDataEntity.converterDe(contaDeAcao, contaEntity.id))
                                                .collect(Collectors.toSet());
                                                
            }, () -> contaEntity.contasDeAcao = new HashSet<ContaDeAcaoDataEntity>() );

        contaEntity.dataUltimaAtualizacao = conta.getDataUltimaAtualizacao();
        contaEntity.email = conta.getEmail();
        contaEntity.saldoDisponivel = conta.getSaldoAtual();

        return contaEntity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

}
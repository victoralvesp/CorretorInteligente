package com.bolsafacil.corretorinteligente.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "monitoramentos")
public class Monitoramento {

    @Id
    Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_monitoramento")
    Set<AcaoDoMonitoramento> acoesMonitoradas;
    
    @Column(nullable = false, name = "data_evento")
    LocalDateTime data;

    @Column(nullable = false, name = "data_registro")
    LocalDateTime dataRegistro;

    @Column
    Boolean excluido;


    public Monitoramento(Set<AcaoDoMonitoramento> acoesMonitoradas, LocalDateTime data) {
        this(acoesMonitoradas, data, 0);
    }
    public Monitoramento(Set<AcaoDoMonitoramento> acoesMonitoradas, LocalDateTime data, long id) {
        this.acoesMonitoradas = acoesMonitoradas;
        this.data = data;
        this.id = id;
    }        

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        var other = (Monitoramento) obj;

        return id == other.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * @return as acoes monitoradas nesse monitoramento
     */
    public Set<AcaoDoMonitoramento> getAcoesMonitoradas() {
        return acoesMonitoradas;
    }
    /**
     * @return a data do evento de monitoramento
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * @return data do registro em banco do monitoramento
     */
    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }
    /**
     * @param dataRegistro a data e horo em que o monitoramento foi registrado
     */
    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    /**
     * @return o id
     */
    public Long getId() {
        return id;
    }
    
}
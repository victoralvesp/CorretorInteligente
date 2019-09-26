package com.bolsafacil.corretorinteligente.domain;

import java.time.LocalDateTime;
import java.util.Set;

// @Entity
// @Table(name = "eventos_de_observacao_de_acoes")
public class ObservacaoDeAcoes {

    // @Id
    long id;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_evento_observacao")
    Set<AcaoObservada> acoesObservadas;
    
    // @Column(nullable = false, name = "data_evento")
    LocalDateTime data;

    // @Column(nullable = false, name = "data_registro")
    LocalDateTime dataRegistro;

    // @Column
    boolean excluido;


    public ObservacaoDeAcoes(Set<AcaoObservada> acoesMonitoradas, LocalDateTime data) {
        this(acoesMonitoradas, data, 0);
    }
    public ObservacaoDeAcoes(Set<AcaoObservada> acoesMonitoradas, LocalDateTime data, long id) {
        this.acoesObservadas = acoesMonitoradas;
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
        var other = (ObservacaoDeAcoes) obj;

        return id == other.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    /**
     * @return as acoes monitoradas nesse monitoramento
     */
    public Set<AcaoObservada> getAcoesMonitoradas() {
        return acoesObservadas;
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
    public long getId() {
        return id;
    }
    
}
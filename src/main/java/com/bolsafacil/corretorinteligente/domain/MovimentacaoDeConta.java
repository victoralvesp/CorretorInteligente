package com.bolsafacil.corretorinteligente.domain;

import java.time.LocalDateTime;

/**
 * MovimentacaoDeConta
 */
public interface MovimentacaoDeConta {

    public Number getValorMovimentado();
    public LocalDateTime getDataMovimentacao();
    
}
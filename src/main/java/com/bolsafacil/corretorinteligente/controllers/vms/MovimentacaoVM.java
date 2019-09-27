package com.bolsafacil.corretorinteligente.controllers.vms;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;

public class MovimentacaoVM {

    public TipoMovimentacao tipo;
    public String data;
    public String quantidadeDeAcoes;
    public String valorMovimentado;
    public String idConta;
    public String empresa;

    public MovimentacaoVM(MovimentacaoDeConta mov) {
        tipo = mov.getTipoMovimentacao();
        idConta = String.valueOf(mov.getContaMovimentada().getId());
        valorMovimentado = mov.getValorMovimentado().toString();
        quantidadeDeAcoes = mov.getQuantidadeDeAcoesMovimentada().toString();
        empresa = mov.getEmpresaDaAcaoMovimentada();
        data = mov.getDataMovimentacao().toString();
    }

}
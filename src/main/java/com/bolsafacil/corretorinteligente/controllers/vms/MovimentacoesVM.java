package com.bolsafacil.corretorinteligente.controllers.vms;

import java.util.Collection;
import java.util.stream.Collectors;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

public class MovimentacoesVM {
    public Collection<MovimentacaoVM> movimentacoes;
    public MovimentacoesVM(Collection<MovimentacaoVM> movimentacoesVM) {
        this.movimentacoes = movimentacoesVM;
    }

    public static MovimentacoesVM converterParaViewModel(
            Collection<? extends MovimentacaoDeConta> movimentacoesDeConta) {
        var movimentacoesVM = movimentacoesDeConta.stream().map(mov -> new MovimentacaoVM(mov)).collect(Collectors.toList());
        return new MovimentacoesVM(movimentacoesVM);
    }
}

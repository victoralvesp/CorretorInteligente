package com.bolsafacil.corretorinteligente.repositorios.implementacoes;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.repositorios.ContasRepository;

import org.springframework.stereotype.Component;

/**
 * ContasRepositoryImpl
 */
@Component
public class ContasRepositoryImpl implements ContasRepository {


    @Override
    public void salvar(Conta... contasMovimentadas) {

    }

    @Override
    public Collection<ContaPessoal> listar() {
        return null;
    }

    @Override
    public ContaPessoal buscar(String email) {
        return null;
    }

    
}
package br.com.bootcamp.zup.fatura.parcelarfatura;

import br.com.bootcamp.zup.fatura.Fatura;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ParcelamentoRequest {

    @NotNull
    @Positive
    private int quantidade;


    public int getQuantidade() {
        return quantidade;
    }

    public Parcela toModel(Fatura fatura) {
        return new Parcela(fatura, quantidade);
    }
}

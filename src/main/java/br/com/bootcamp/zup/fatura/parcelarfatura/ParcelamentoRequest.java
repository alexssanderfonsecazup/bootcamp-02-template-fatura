package br.com.bootcamp.zup.fatura.parcelarfatura;

import br.com.bootcamp.zup.fatura.Fatura;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ParcelamentoRequest {

    @NotNull
    @Positive
    private int quantidade;

    @NotNull
    @Positive
    private BigDecimal valor;


    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Parcela toModel(Fatura fatura) {
        return new Parcela(fatura, quantidade, valor);
    }
}

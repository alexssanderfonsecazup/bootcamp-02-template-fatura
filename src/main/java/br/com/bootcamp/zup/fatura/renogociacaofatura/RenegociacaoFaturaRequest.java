package br.com.bootcamp.zup.fatura.renogociacaofatura;

import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.parcelarfatura.Parcela;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RenegociacaoFaturaRequest {

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

    public Renegociacao toModel(Fatura fatura) {
        return new Renegociacao(quantidade,valor,fatura);
    }
}

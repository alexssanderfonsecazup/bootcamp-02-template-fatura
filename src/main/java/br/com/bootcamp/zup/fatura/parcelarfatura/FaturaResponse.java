package br.com.bootcamp.zup.fatura.parcelarfatura;

import br.com.bootcamp.zup.fatura.Fatura;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;

public class FaturaResponse {

    private UUID id;
    private String mes;
    private BigDecimal total;

    public FaturaResponse(Fatura fatura){
        this.id = fatura.getId();
        this.mes = fatura.getMesReferencia().getDisplayName(TextStyle.FULL, Locale.getDefault());
        this.total = fatura.calculaValorFatura();
    }


    public UUID getId() {
        return id;
    }

    public String getMes() {
        return mes;
    }

    public BigDecimal getTotal() {
        return total;
    }
}

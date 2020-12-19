package br.com.bootcamp.zup.fatura.parcelarfatura;

import br.com.bootcamp.zup.fatura.Fatura;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;

public class FaturaResponse {

    private UUID id;
    private String mes;
    private int ano;
    private BigDecimal total;

    public FaturaResponse(Fatura fatura){
        this.id = fatura.getId();
        this.mes = fatura.getMes().getDisplayName(TextStyle.FULL, Locale.getDefault());
        this.ano = fatura.getAno();
        this.total = fatura.calculaValorFatura();
    }


    public UUID getId() {
        return id;
    }

    public String getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public BigDecimal getTotal() {
        return total;
    }
}

package br.com.bootcamp.zup.fatura.parcelarfatura;

import java.math.BigDecimal;

public class ParcelaResponse {

    private FaturaResponse fatura;
    private int quantidadeParcelas;
    private BigDecimal valorParcela;
    private String status;

    public ParcelaResponse(Parcela parcela) {
        this.fatura = new FaturaResponse(parcela.getFatura());
        this.quantidadeParcelas = parcela.getQuantidade();
        this.valorParcela  = parcela.getValorParcela();
        this.status = parcela.getStatus().toString();
    }

    public FaturaResponse getFatura() {
        return fatura;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }
}

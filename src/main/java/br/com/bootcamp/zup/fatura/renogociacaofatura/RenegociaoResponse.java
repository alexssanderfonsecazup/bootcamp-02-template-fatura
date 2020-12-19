package br.com.bootcamp.zup.fatura.renogociacaofatura;

import br.com.bootcamp.zup.fatura.parcelarfatura.FaturaResponse;

import java.math.BigDecimal;

public class RenegociaoResponse {


    private FaturaResponse fatura;
    private int quantidadeParcelas;
    private BigDecimal valorParcela;
    private String status;

    public RenegociaoResponse(Renegociacao renegociacao) {
        this.fatura = new FaturaResponse(renegociacao.getFatura());
        this.quantidadeParcelas = renegociacao.getQuantidade();
        this.valorParcela = renegociacao.getValorParcela();
        this.status = renegociacao.getStatus().toString();
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
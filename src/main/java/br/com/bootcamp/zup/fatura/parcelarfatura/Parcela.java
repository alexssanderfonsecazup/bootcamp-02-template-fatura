package br.com.bootcamp.zup.fatura.parcelarfatura;

import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.consometransacao.Transacao;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Parcela {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @OneToOne
    private Fatura fatura;

    @NotNull
    @Positive
    private int quantidade;

    private StatusParcelamentoEnum status;

    @CreationTimestamp
    private Instant criadoEm;

    @UpdateTimestamp
    private Instant modificadoEm;

    private BigDecimal valorParcela;

    @Deprecated
    public Parcela(){}

    public Parcela(@NotNull Fatura fatura, @NotNull @Positive int quantidade) {
        this.fatura = fatura;
        this.quantidade = quantidade;
        this.valorParcela = calculaValorParcela();
    }

    private BigDecimal calculaValorParcela() {
        return Transacao.somaTransacoes(fatura.getTransacoes()).divide(new BigDecimal(quantidade))
                .setScale(2, RoundingMode.CEILING);
    }

    public UUID getId() {
        return id;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setStatus(StatusParcelamentoEnum status) {
        this.status = status;
    }
}

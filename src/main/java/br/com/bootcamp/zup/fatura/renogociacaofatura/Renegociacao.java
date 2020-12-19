package br.com.bootcamp.zup.fatura.renogociacaofatura;


import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.parcelarfatura.StatusParcelamentoEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Renegociacao {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Positive
    private int quantidade;

    @NotNull
    @Positive
    private BigDecimal valorParcela;

    private LocalDate inicioPagamentoAte;

    private StatusRenegociacaoEnum status;

    @OneToOne
    private Fatura fatura;

    public Renegociacao() {
    }


    public Renegociacao(@NotNull @Positive int quantidade, @NotNull @Positive BigDecimal valorParcela, Fatura fatura) {
        this.quantidade = quantidade;
        this.valorParcela = valorParcela;
        this.fatura = fatura;
        this.inicioPagamentoAte = definePrazoParaInicioDoPagamento();
    }

    private LocalDate definePrazoParaInicioDoPagamento() {
        return LocalDate.of(fatura.getAno(), fatura.getMes().plus(3l), 10);
    }


    public UUID getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDate getInicioPagamentoAte() {
        return inicioPagamentoAte;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setStatus(StatusRenegociacaoEnum status) {
        this.status = status;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public StatusRenegociacaoEnum getStatus() {
        return status;
    }
}

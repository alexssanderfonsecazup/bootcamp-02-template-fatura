package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.consometransacao.Transacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Entity
public class Fatura {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany
    private List<Transacao> transacoes;

    private Month mes;

    private int ano;

    private LocalDate vencimento;

    @ManyToOne
    private Cartao cartao;


    @Deprecated
    public Fatura() {
    }

    public Fatura(@NotNull Cartao cartao, Month mes, int ano) {
        this.cartao = cartao;
        this.mes = mes;
        this.ano = ano;
        this.vencimento = LocalDate.of(ano, mes.plus(1l),10);
    }

    public BigDecimal calculaValorFatura(){
        return Transacao.somaTransacoes(transacoes);
    }


    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public UUID getId() {
        return id;
    }

    public Month getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }
}

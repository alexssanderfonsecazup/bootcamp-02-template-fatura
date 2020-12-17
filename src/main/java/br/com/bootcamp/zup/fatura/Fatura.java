package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.consometransacao.Transacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@Entity
public class Fatura {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany
    private List<Transacao> transacoes;

    private Month mesReferencia;

    @ManyToOne
    private Cartao cartao;


    @Deprecated
    public Fatura() {
    }

    public Fatura(@NotNull Cartao cartao, Month mesReferencia) {
        this.cartao = cartao;
        this.mesReferencia = mesReferencia;
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

    public Month getMesReferencia() {
        return mesReferencia;
    }
}

package br.com.bootcamp.zup.fatura.consometransacao;


import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Cartao {

    @Id
    private String id;
    @NotBlank
    private String email;

    private BigDecimal limite;

    @Deprecated
    public Cartao() {
    }


    public Cartao(@NotNull String id, @NotBlank String email) {
        this.id = id;
        this.email = email;
    }

    public BigDecimal calculaSaldoDisponivel(List<Transacao> transacoesDoMes) {
        Assert.isTrue(limite != null, "O limite precisa estar definido para calcular o saldo");
        return limite.subtract(Transacao.somaTransacoes(transacoesDoMes));
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public String getId() {
        return id;
    }

    public void atualizaLimiteCasoAlterado(String limite) {
        BigDecimal novoLimite = new BigDecimal(limite);
        if (!this.limite.equals(novoLimite)) {
            this.limite = novoLimite;
        }
    }
}

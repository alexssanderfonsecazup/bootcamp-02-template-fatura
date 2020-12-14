package br.com.bootcamp.zup.fatura;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.Month;
import java.util.Objects;

@Embeddable
public class FaturaPk implements Serializable {
    private Month mes;
    private String idCartao;

    public FaturaPk(){}
    public FaturaPk(Month month, String idCartao) {
        this.mes = month;
        this.idCartao = idCartao;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaturaPk faturaPk = (FaturaPk) o;
        return mes == faturaPk.mes &&
                Objects.equals(idCartao, faturaPk.idCartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mes, idCartao);
    }

    public Month getMes() {
        return mes;
    }

    public String getIdCartao() {
        return idCartao;
    }
}

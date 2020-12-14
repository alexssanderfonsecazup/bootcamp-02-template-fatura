package br.com.bootcamp.zup.fatura.consometransacao;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {

    @Id
    private String id;
    @NotBlank
    private String email;


    @Deprecated
    public Cartao() {
    }


    public Cartao(@NotNull String id, @NotBlank String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }
}
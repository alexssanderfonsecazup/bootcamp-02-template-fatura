package br.com.bootcamp.zup.fatura.consometransacao.kafka.response;


import br.com.bootcamp.zup.fatura.consometransacao.Cartao;

public class EventoDeTransacaoCartao {
    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public Cartao toModel(){
        return new Cartao(id,email);
    }

}

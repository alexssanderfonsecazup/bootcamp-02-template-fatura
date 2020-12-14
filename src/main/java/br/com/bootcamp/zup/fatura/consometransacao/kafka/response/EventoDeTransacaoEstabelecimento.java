package br.com.bootcamp.zup.fatura.consometransacao.kafka.response;

public class EventoDeTransacaoEstabelecimento {
    private String nome;
    private String cidade;
    private String endereco;

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEndereco() {
        return endereco;
    }

}

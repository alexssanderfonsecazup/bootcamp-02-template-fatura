package br.com.bootcamp.zup.fatura.detalhefatura;

import br.com.bootcamp.zup.fatura.consometransacao.Estabelecimento;

public class EstabelecimentoResponse {

    private String nome;
    private String endereco;
    private String cidade;

    public EstabelecimentoResponse(Estabelecimento estabelecimento){
        this.nome = estabelecimento.getNome();
        this.endereco= estabelecimento.getEndereco();
        this.cidade = estabelecimento.getCidade();
    }

    public String getCidade() {
        return cidade;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }
}

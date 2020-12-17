package br.com.bootcamp.zup.fatura.consometransacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,String> {

    List<Transacao> findFirst10ByCartaoOrderByEfetivadaEmAsc(Cartao cartao);
}

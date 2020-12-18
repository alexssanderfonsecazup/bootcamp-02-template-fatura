package br.com.bootcamp.zup.fatura.parcelarfatura;


import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.integracao.CartaoClient;
import br.com.bootcamp.zup.fatura.integracao.ParcelaCartaoIntegracaoRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes/{idCartao}/faturas/{idFatura}/parcelas")
public class ParcelaFaturaController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CartaoClient cartaoClient;

    private Logger logger = LoggerFactory.getLogger(ParcelaFaturaController.class);

    @PostMapping
    @Transactional
    public ResponseEntity<?> parcelarFatura(@PathVariable String idCartao, @PathVariable UUID idFatura
            , @RequestBody @Valid ParcelamentoRequest parcelamentoRequest, UriComponentsBuilder uriBuilder) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        Fatura fatura = entityManager.find(Fatura.class, idFatura);
        if (fatura == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Iniciando processo de parcelamento da fatura {} para o cartao {} ", idFatura, idCartao);

        Parcela parcela = parcelamentoRequest.toModel(fatura);
        entityManager.persist(parcela);
        logger.info("Parcelamento {} salvo no banco de dados", parcela.getId());

        try {
            logger.info("Sinalizando a api legado do parcelamento {}", parcela.getId());
            cartaoClient.parcelaCartao(idCartao, new ParcelaCartaoIntegracaoRequest(idCartao.toString(), parcela.getQuantidade(), parcela.getValorParcela()));
            parcela.setStatus(StatusParcelamentoEnum.APROVADO);
            logger.info("Parcelamento {} aprovado pela api legado", parcela.getId());
        } catch (FeignException e) {
            parcela.setStatus(StatusParcelamentoEnum.NEGADO);
            logger.info("Parcelamento {} não foi  aprovado pela api legado", parcela.getId());
            logger.debug("Status: ", e.status());
            logger.debug("Content: ",e.contentUTF8());

        }


        URI uri = uriBuilder.path("/cartoes/{idCartao}/faturas/{idFatura}/parcelas/{idParcela}")
                .buildAndExpand(idCartao, idFatura, parcela.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{idParcela}")
    public ResponseEntity<?> consultaParcela(@PathVariable String idCartao,
                                             @PathVariable UUID idFatura,
                                             @PathVariable UUID idParcela) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        Fatura fatura = entityManager.find(Fatura.class, idFatura);
        if (fatura == null) {
            return ResponseEntity.notFound().build();
        }

        Parcela parcela = entityManager.find(Parcela.class, idParcela);
        if (parcela == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ParcelaResponse(parcela));

    }

}

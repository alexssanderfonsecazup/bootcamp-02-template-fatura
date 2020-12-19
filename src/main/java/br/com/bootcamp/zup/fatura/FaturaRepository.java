package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

public interface FaturaRepository extends JpaRepository<Fatura, UUID> {

    Optional<Fatura> findByCartaoAndMesAndAno(Cartao cartao, Month mes, int ano);
}

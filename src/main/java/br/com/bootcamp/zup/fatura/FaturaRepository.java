package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.FaturaPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepository extends JpaRepository<Fatura, FaturaPk> {
}

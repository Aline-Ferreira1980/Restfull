package br.com.dititalinnovation.restfull.repository;

import br.com.dititalinnovation.restfull.entity.SoldadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldadoRepository  extends JpaRepository<SoldadoEntity, Long> {
}

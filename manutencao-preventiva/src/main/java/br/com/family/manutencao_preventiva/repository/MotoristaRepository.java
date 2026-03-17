package br.com.family.manutencao_preventiva.repository;

import br.com.family.manutencao_preventiva.domain.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    Optional<UserDetails> findByCpf(String cpf);
}

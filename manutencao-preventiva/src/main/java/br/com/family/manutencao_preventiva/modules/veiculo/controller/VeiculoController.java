package br.com.family.manutencao_preventiva.modules.veiculo.controller;

import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoProntuarioResumoDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.modules.veiculo.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> criar(@RequestBody @Valid VeiculoRequestDTO veiculoRequestDTO) {
        var veiculo = veiculoService.criar(veiculoRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
    }

    @GetMapping("/{id}/prontuario/resumo")
    public ResponseEntity<VeiculoProntuarioResumoDTO> getResumoProntuario(@PathVariable Long id) {
        VeiculoProntuarioResumoDTO resumo = veiculoService.buscarResumoProntuario(id);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        return ResponseEntity.ok(veiculoService.listar());
    }

}

package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.dto.request.VeiculoRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.VeiculoResponseDTO;
import br.com.family.manutencao_preventiva.service.VeiculoService;
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

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        return ResponseEntity.ok(veiculoService.listar());
    }

}

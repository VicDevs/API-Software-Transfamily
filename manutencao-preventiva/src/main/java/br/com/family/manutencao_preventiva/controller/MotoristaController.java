package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.dto.request.MotoristaRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.MotoristaResponseDTO;
import br.com.family.manutencao_preventiva.service.MotoristaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<MotoristaResponseDTO> criar(@RequestBody @Valid MotoristaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MotoristaResponseDTO>> listar() {
        return ResponseEntity.ok(motoristaService.listar());
    }
}

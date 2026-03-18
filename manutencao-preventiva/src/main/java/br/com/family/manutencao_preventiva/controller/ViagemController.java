package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResponseDTO;
import br.com.family.manutencao_preventiva.service.ViagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;


    @PostMapping("/iniciar")
    public ResponseEntity<ViagemResponseDTO> iniciar(@RequestBody @Valid ChecklistRequestDTO dto,
                                                     @AuthenticationPrincipal Motorista motorista) {

        ViagemResponseDTO response = viagemService.iniciarViagem(dto, motorista);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/retorno")
    public ResponseEntity<ViagemResponseDTO> abrirRetorno(@PathVariable("id") Long viagemId,
                                                          @RequestBody @Valid ChecklistRequestDTO dto) {

        ViagemResponseDTO response = viagemService.abrirChecklistRetorno(viagemId, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ViagemResponseDTO> confirmarFinalizacao(@PathVariable("id") Long viagemId) {

        ViagemResponseDTO response = viagemService.finalizarViagem(viagemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativa")
    public ResponseEntity<ViagemResponseDTO> buscarAtiva(@AuthenticationPrincipal Motorista motorista) {
        ViagemResponseDTO response = viagemService.buscarViagemAtiva(motorista);
        return ResponseEntity.ok(response);
    }
}

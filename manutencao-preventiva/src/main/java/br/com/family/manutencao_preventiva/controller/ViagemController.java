package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.modules.motorista.domain.model.Motorista;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemDetalhadaDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemResumoDTO;
import br.com.family.manutencao_preventiva.service.ViagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;


    @PostMapping("/iniciar")
    public ResponseEntity<ViagemDetalhadaDTO> iniciar(@RequestBody @Valid ChecklistRequestDTO dto,
                                                     @AuthenticationPrincipal Motorista motorista) {

        ViagemDetalhadaDTO response = viagemService.iniciarViagem(dto, motorista);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/retorno")
    public ResponseEntity<ViagemDetalhadaDTO> abrirRetorno(@PathVariable("id") Long viagemId,
                                                        @RequestBody @Valid ChecklistRequestDTO dto) {
        ViagemDetalhadaDTO response = viagemService.abrirChecklistRetorno(viagemId, dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ViagemResumoDTO> confirmarFinalizacao(@PathVariable("id") Long viagemId) {

        ViagemResumoDTO response = viagemService.finalizarViagem(viagemId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativa")
    public ResponseEntity<ViagemResumoDTO> buscarAtiva(@AuthenticationPrincipal Motorista motorista) {
        ViagemResumoDTO response = viagemService.buscarViagemAtiva(motorista.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historico")
    public ResponseEntity<Page<ViagemResumoDTO>> listarHistorico(
            @AuthenticationPrincipal Motorista motorista,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Agora passamos 'inicio' e 'fim' para o service
        Page<ViagemResumoDTO> historico = viagemService.listarHistorico(
                motorista.getId(),
                inicio,
                fim,
                page,
                size
        );
        return ResponseEntity.ok(historico);
    }
}

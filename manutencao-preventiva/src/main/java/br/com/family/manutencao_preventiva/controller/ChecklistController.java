package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.domain.model.Motorista;
import br.com.family.manutencao_preventiva.domain.model.User;
import br.com.family.manutencao_preventiva.dto.request.ChecklistRequestDTO;
import br.com.family.manutencao_preventiva.dto.request.ChecklistUpdateDTO;
import br.com.family.manutencao_preventiva.dto.response.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.service.ChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Void> salvarRespostas(
            @PathVariable Long id,
            @RequestBody @Valid ChecklistUpdateDTO dto) {

        checklistService.salvarEFinalizar(id, dto);
        return ResponseEntity.noContent().build();
    }
}

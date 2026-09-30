package br.com.family.manutencao_preventiva.modules.checklist.controller;

import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistUpdateDTO;
import br.com.family.manutencao_preventiva.modules.checklist.dto.ChecklistResponseDTO;
import br.com.family.manutencao_preventiva.modules.checklist.service.ChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> buscarPorId(@PathVariable Long id) {
        ChecklistResponseDTO dto = checklistService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Void> salvarRespostas(
            @PathVariable Long id,
            @RequestBody @Valid ChecklistUpdateDTO dto) {

        checklistService.salvarEFinalizar(id, dto);
        return ResponseEntity.noContent().build();
    }
}

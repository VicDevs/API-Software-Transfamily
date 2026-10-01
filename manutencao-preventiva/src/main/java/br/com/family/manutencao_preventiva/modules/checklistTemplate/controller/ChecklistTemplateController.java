package br.com.family.manutencao_preventiva.modules.checklistTemplate.controller;

import br.com.family.manutencao_preventiva.modules.veiculo.domain.enums.TipoVeiculo;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateRequestDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.dto.ChecklistTemplateResponseDTO;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.repository.ChecklistTemplateRepository;
import br.com.family.manutencao_preventiva.modules.checklistTemplate.service.ChecklistTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class ChecklistTemplateController {

    private final ChecklistTemplateService service;

    private final ChecklistTemplateRepository repository;

    @PostMapping
    public ResponseEntity<ChecklistTemplateResponseDTO> criar(@RequestBody @Valid ChecklistTemplateRequestDTO dto) {
        ChecklistTemplateResponseDTO response = service.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChecklistTemplateResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ChecklistTemplateResponseDTO>> listarAtivosPorTipo(@RequestParam TipoVeiculo tipo) {
        return ResponseEntity.ok(service.buscarAtivosPorTipo(tipo));
    }
}

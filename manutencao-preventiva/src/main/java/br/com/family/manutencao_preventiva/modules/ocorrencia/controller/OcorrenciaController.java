package br.com.family.manutencao_preventiva.modules.ocorrencia.controller;

import br.com.family.manutencao_preventiva.modules.ocorrencia.dto.OcorrenciaRequestDTO;
import br.com.family.manutencao_preventiva.modules.ocorrencia.dto.OcorrenciaResponseDTO;
import br.com.family.manutencao_preventiva.modules.ocorrencia.repository.OcorrenciaRepository;
import br.com.family.manutencao_preventiva.modules.ocorrencia.service.OcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;
    private final OcorrenciaRepository ocorrenciaRepository;

    @PostMapping
    public ResponseEntity<OcorrenciaResponseDTO> registrar(@RequestBody OcorrenciaRequestDTO dto) {
        OcorrenciaResponseDTO response = ocorrenciaService.registrarOcorrencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    // Retorna todas as ocorrências de uma viagem específica (Para a tela de detalhes da viagem)
//    @GetMapping("/viagem/{viagemId}")
//    public ResponseEntity<List<OcorrenciaResponseDTO>> buscarPorViagem(@PathVariable Long viagemId) {
//        List<OcorrenciaResponseDTO> lista = ocorrenciaRepository.findByViagemIdOrderByDataHoraDesc(viagemId)
//                .stream()
//                .map(ocorrenciaService::montarResponseDTO) // Reaproveita o método do Service
//                .toList();
//
//        return ResponseEntity.ok(lista);
//    }
//
//    // Retorna as ocorrências mais recentes de TODA A FROTA (Para o Dashboard)
//    @GetMapping("/recentes")
//    public ResponseEntity<List<OcorrenciaResponseDTO>> buscarRecentesParaDashboard() {
//        // Exemplo: Busca as últimas 20 ocorrências globais
//        List<OcorrenciaResponseDTO> lista = ocorrenciaRepository.findTop20ByOrderByDataHoraDesc()
//                .stream()
//                .map(ocorrenciaService::montarResponseDTO)
//                .toList();
//
//        return ResponseEntity.ok(lista);
//    }
}
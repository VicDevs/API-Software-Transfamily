package br.com.family.manutencao_preventiva.controller;

import br.com.family.manutencao_preventiva.dto.response.DashboardResumoDTO;
import br.com.family.manutencao_preventiva.dto.response.OcorrenciaResumoDTO;
import br.com.family.manutencao_preventiva.dto.response.ViagemAtivaDTO;
import br.com.family.manutencao_preventiva.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoDTO> obterResumo() {
        DashboardResumoDTO resumo = dashboardService.gerarResumo();
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/viagens-ativas")
    public ResponseEntity<List<ViagemAtivaDTO>> buscarViagensAtivas() {
        return ResponseEntity.ok(dashboardService.buscarViagensAtivas());
    }

    @GetMapping("/ocorrencias-recentes")
    public ResponseEntity<List<OcorrenciaResumoDTO>> buscarOcorrenciasRecentes() {
        return ResponseEntity.ok(dashboardService.buscarOcorrenciasRecentes());
    }
}
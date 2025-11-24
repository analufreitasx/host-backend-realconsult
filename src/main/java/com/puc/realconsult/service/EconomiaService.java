package com.puc.realconsult.service;

import com.puc.realconsult.dto.EconomiaDashboardDTO;
import com.puc.realconsult.dto.EconomiaResumoMensalDTO;
import com.puc.realconsult.model.realConsult.FuncionarioModel;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.repository.realConsult.AuditoriaRepository;
import com.puc.realconsult.repository.realConsult.FuncionarioRepository;
import com.puc.realconsult.repository.vtRealRepository.ClienteApiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EconomiaService {

    private final FuncionarioRepository funcionarioRepository;
    private final AuditoriaRepository auditoriaRepository;
    private final ClienteApiRepository clienteApiRepository;

    private double arredondarDuasCasas(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public EconomiaDashboardDTO obterEconomiaDashboard(
            Long clienteId,
            java.time.LocalDate dataInicio,
            java.time.LocalDate dataFim
    ) {

        java.time.LocalDateTime inicioDateTime =
                dataInicio != null ? dataInicio.atStartOfDay() : null;
        java.time.LocalDateTime fimDateTime =
                dataFim != null ? dataFim.atTime(23, 59, 59) : null;

        java.util.List<FuncionarioModel> funcionarios = funcionarioRepository.findByFiltros(
                clienteId,
                inicioDateTime,
                fimDateTime
        );

        if (funcionarios.isEmpty()) {
            return new EconomiaDashboardDTO(
                    clienteId,
                    dataInicio,
                    dataFim,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    java.util.List.of()
            );
        }

        ClienteModel cliente = null;
        if (clienteId != null) {
            cliente = clienteApiRepository.findById(clienteId).orElse(null);
        }

        double economiaPrevistaPeriodo = 0.0;
        double economiaRealPeriodo = 0.0;

        java.util.Map<java.time.YearMonth, double[]> mapaMensal = new java.util.HashMap<>();

        Integer anoReferencia;
        if (dataFim != null) {
            anoReferencia = dataFim.getYear();
        } else if (dataInicio != null) {
            anoReferencia = dataInicio.getYear();
        } else {
            anoReferencia = java.time.LocalDate.now().getYear();
        }

        double totalAcumuladoAno = 0.0;

        for (FuncionarioModel f : funcionarios) {

            double valorDiario =
                    (f.getTarifaLinhaUmIda()       != null ? f.getTarifaLinhaUmIda()       : 0.0) +
                            (f.getTarifaLinhaDoisIda()     != null ? f.getTarifaLinhaDoisIda()     : 0.0) +
                            (f.getTarifaLinhaTresIda()     != null ? f.getTarifaLinhaTresIda()     : 0.0) +
                            (f.getTarifaLinhaQuatroIda()   != null ? f.getTarifaLinhaQuatroIda()   : 0.0) +
                            (f.getTarifaLinhaUmVolta()     != null ? f.getTarifaLinhaUmVolta()     : 0.0) +
                            (f.getTarifaLinhaDoisVolta()   != null ? f.getTarifaLinhaDoisVolta()   : 0.0) +
                            (f.getTarifaLinhaTresVolta()   != null ? f.getTarifaLinhaTresVolta()   : 0.0) +
                            (f.getTarifaLinhaQuatroVolta() != null ? f.getTarifaLinhaQuatroVolta() : 0.0);

            double custoImplantado = f.getDiasMes() * valorDiario;

            double custoAtual = f.getCustoAtual() != null ? f.getCustoAtual() : 0.0;
            double custoProposto = f.getCustoProposto() != null ? f.getCustoProposto() : 0.0;

            double economiaPrevista = custoAtual - custoProposto;
            double economiaReal = custoAtual - custoImplantado;

            economiaPrevistaPeriodo += economiaPrevista;
            economiaRealPeriodo += economiaReal;

            java.time.LocalDateTime dataAuditoria = f.getAuditoria().getCreationDate();
            java.time.YearMonth ym = java.time.YearMonth.from(dataAuditoria);

            double[] acumuladoMes = mapaMensal.computeIfAbsent(ym, k -> new double[2]);
            acumuladoMes[0] += economiaPrevista;
            acumuladoMes[1] += economiaReal;

            if (anoReferencia != null && dataAuditoria.getYear() == anoReferencia) {
                totalAcumuladoAno += economiaReal;
            }
        }

        economiaPrevistaPeriodo = arredondarDuasCasas(economiaPrevistaPeriodo);
        economiaRealPeriodo     = arredondarDuasCasas(economiaRealPeriodo);
        totalAcumuladoAno       = arredondarDuasCasas(totalAcumuladoAno);

        double diferencaPercentualPeriodo = ((economiaRealPeriodo - economiaPrevistaPeriodo) / economiaPrevistaPeriodo) * 100.0;

        diferencaPercentualPeriodo = arredondarDuasCasas(diferencaPercentualPeriodo);

        java.util.List<EconomiaResumoMensalDTO> economiasMensais = mapaMensal.entrySet()
                .stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .map(e -> new EconomiaResumoMensalDTO(
                        e.getKey().getYear(),
                        e.getKey().getMonthValue(),
                        arredondarDuasCasas(e.getValue()[0]),
                        arredondarDuasCasas(e.getValue()[1])
                ))
                .collect(java.util.stream.Collectors.toList());

        return new EconomiaDashboardDTO(
                cliente != null ? cliente.getIdCliente() : clienteId,
                dataInicio,
                dataFim,
                economiaPrevistaPeriodo,
                economiaRealPeriodo,
                diferencaPercentualPeriodo,
                totalAcumuladoAno,
                economiasMensais
        );
    }


}

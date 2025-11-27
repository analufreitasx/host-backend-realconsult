
package com.puc.realconsult.service;

import com.puc.realconsult.dto.HistoricoConsultaDTO;
import com.puc.realconsult.dto.RequisicoesDashboardClienteDTO;
import com.puc.realconsult.model.vtRealModel.AuditoriaResultadoModel;
import com.puc.realconsult.model.vtRealModel.ClienteModel;
import com.puc.realconsult.model.vtRealModel.JobsModel;
import com.puc.realconsult.repository.vtRealRepository.AuditoriaResultadoRepository;
import com.puc.realconsult.repository.vtRealRepository.ClienteApiRepository;
import com.puc.realconsult.repository.vtRealRepository.JobsRepository;
import com.puc.realconsult.repository.vtRealRepository.UsuarioApiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class RequisicoesService {

    private final JobsRepository jobsRepository;
    private final AuditoriaResultadoRepository auditoriaResultadoRepository;
    private final ClienteApiRepository clienteRepository;
    private final UsuarioApiRepository usuarioApiRepository;

    public List<RequisicoesDashboardClienteDTO> obterRequisicoesDashboard(
            String status,
            Integer ano,
            LocalDate inicio,
            LocalDate fim
    ) {
        Periodo periodo = resolverPeriodo(ano, inicio, fim);

        List<JobsModel> jobs = jobsRepository.findByFiltros(
                status,
                periodo.inicio,
                periodo.fim
        );

        List<AuditoriaResultadoModel> auditorias = auditoriaResultadoRepository.findByFiltros(
                status,
                periodo.inicio,
                periodo.fim
        );

        Map<Long, RequisicoesDashboardClienteDTO> mapa = new HashMap<>();

        for (JobsModel job : jobs) {
            Long idCliente = extrairIdCliente(job);
            if (idCliente == null) {
                continue;
            }
            RequisicoesDashboardClienteDTO dto = mapa.computeIfAbsent(
                    idCliente,
                    id -> criarDTOBase(id)
            );
            dto.setConsultasRealizadas(dto.getConsultasRealizadas() + 1);
        }

        for (AuditoriaResultadoModel auditoria : auditorias) {
            Long idCliente = Long.valueOf(extrairIdCliente(auditoria));
            if (idCliente == null) {
                continue;
            }
            RequisicoesDashboardClienteDTO dto = mapa.computeIfAbsent(
                    idCliente,
                    id -> criarDTOBase(id)
            );
            dto.setConsultasRealizadas(dto.getConsultasRealizadas() + 1);
        }

        for (RequisicoesDashboardClienteDTO dto : mapa.values()) {
            int contratadas = dto.getConsultasContratadas() != null ? dto.getConsultasContratadas() : 0;
            int realizadas = dto.getConsultasRealizadas() != null ? dto.getConsultasRealizadas() : 0;
            dto.setSaldoConsultas(contratadas - realizadas);
            dto.setHistoricoConsultas(null);
        }

        return new ArrayList<>(mapa.values());
    }

    public RequisicoesDashboardClienteDTO obterRequisicoesDashboardCliente(
            Long idCliente,
            String status,
            Integer ano,
            LocalDate inicio,
            LocalDate fim
    ) {
        Periodo periodo = resolverPeriodo(ano, inicio, fim);

        List<JobsModel> jobs = jobsRepository.findByClienteEFiltros(
                idCliente,
                status,
                periodo.inicio,
                periodo.fim
        );

        List<AuditoriaResultadoModel> auditorias = auditoriaResultadoRepository.findByClienteEFiltros(
                idCliente,
                status,
                periodo.inicio,
                periodo.fim
        );

        RequisicoesDashboardClienteDTO dto = criarDTOBase(idCliente);

        List<HistoricoConsultaDTO> historico = new ArrayList<>();

        for (JobsModel job : jobs) {
            historico.add(mapFromJobs(job));
        }

        for (AuditoriaResultadoModel auditoria : auditorias) {
            historico.add(mapFromAuditoria(auditoria));
        }

        int realizadas = historico.size();
        int contratadas = dto.getConsultasContratadas() != null ? dto.getConsultasContratadas() : 0;

        dto.setConsultasRealizadas(realizadas);
        dto.setSaldoConsultas(contratadas - realizadas);
        dto.setHistoricoConsultas(historico);

        return dto;
    }

    private RequisicoesDashboardClienteDTO criarDTOBase(Long idCliente) {
        ClienteModel cliente = clienteRepository.findById(idCliente).orElse(null);

        RequisicoesDashboardClienteDTO dto = new RequisicoesDashboardClienteDTO();
        dto.setIdCliente(idCliente);

        Integer consultasContratadas = cliente != null ? cliente.getNumeroConsultas() : null;
        dto.setConsultasContratadas(consultasContratadas != null ? consultasContratadas : 0);
        dto.setConsultasRealizadas(0);
        dto.setSaldoConsultas(consultasContratadas != null ? consultasContratadas : 0);

        return dto;
    }

    private Periodo resolverPeriodo(Integer ano, LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            LocalDateTime iniLdt = inicio.atStartOfDay();
            LocalDateTime fimLdt = fim.atTime(LocalTime.MAX);

//            Timestamp ini = Timestamp.valueOf(iniLdt);
//            Timestamp fi  = Timestamp.valueOf(fimLdt);

            return new Periodo(iniLdt, fimLdt);
        }

        if (ano != null) {
            LocalDateTime iniLdt = LocalDate.of(ano, 1, 1).atStartOfDay();
            LocalDateTime fimLdt = LocalDate.of(ano, 12, 31).atTime(LocalTime.MAX);

//            Timestamp ini = Timestamp.valueOf(iniLdt);
//            Timestamp fi  = Timestamp.valueOf(fimLdt);

            return new Periodo(iniLdt, fimLdt);
        }

        return new Periodo(null, null);
    }

    private Long extrairIdCliente(JobsModel job) {
        if (job == null) {
            return null;
        }
        return usuarioApiRepository.findClienteByLogin(job.getLogin()).getIdCliente();
    }

    private Integer extrairIdCliente(AuditoriaResultadoModel auditoria) {
        if (auditoria == null) {
            return null;
        }
        return auditoria.getIdCliente();
    }

    private HistoricoConsultaDTO mapFromJobs(JobsModel job) {
        HistoricoConsultaDTO dto = new HistoricoConsultaDTO();
        dto.setId(Long.valueOf(job.getIdJob()));
        dto.setDataConsulta(job.getDataInicio());
        dto.setStatus(job.getStatus());
        dto.setOrigem(job.getOriginAddress());
        dto.setDestino(job.getDestinationAddress());
        dto.setResult(String.valueOf(job));
        return dto;
    }

    private HistoricoConsultaDTO mapFromAuditoria(AuditoriaResultadoModel auditoria) {
        HistoricoConsultaDTO dto = new HistoricoConsultaDTO();
        dto.setId(auditoria.getIdResultado());
        dto.setDataConsulta(auditoria.getDataEntrada());
        dto.setStatus(auditoria.getStatus());
        dto.setOrigem(auditoria.getEndOrigem());
        dto.setDestino(auditoria.getEndDestino());
        dto.setResult(String.valueOf(auditoria));
        return dto;
    }

    private static class Periodo {
        private final LocalDateTime inicio;
        private final LocalDateTime fim;

        private Periodo(LocalDateTime inicio, LocalDateTime fim) {
            this.inicio = inicio;
            this.fim = fim;
        }
    }
}
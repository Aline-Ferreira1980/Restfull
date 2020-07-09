package br.com.dititalinnovation.restfull.service;

import br.com.dititalinnovation.restfull.dto.Soldado;
import br.com.dititalinnovation.restfull.entity.SoldadoEntity;
import br.com.dititalinnovation.restfull.repository.SoldadoRepository;
import br.com.dititalinnovation.restfull.request.SoldadoEditRequest;
import br.com.dititalinnovation.restfull.resource.ResourceSoldado;
import br.com.dititalinnovation.restfull.response.SoldadoListResponse;
import br.com.dititalinnovation.restfull.response.SoldadoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resources;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldadoService {

    private SoldadoRepository soldadoRepository;
    private ObjectMapper objectMapper;
    private ResourceSoldado resourceSoldado;

    public SoldadoService(SoldadoRepository soldadoRepository, ObjectMapper objectMapper, ResourceSoldado resourceSoldado) {
        this.soldadoRepository = soldadoRepository;
        this.objectMapper = objectMapper;
        this.resourceSoldado = resourceSoldado;
    }

    public SoldadoResponse buscarSoldado(Long id) {
        SoldadoEntity soldado = soldadoRepository.findById(id).orElseThrow();
        SoldadoResponse soldadoResponse = resourceSoldado.criarLinkDetalhe(soldado);
        return soldadoResponse;
    }

    public void criarSoldado(Soldado soldado){
        SoldadoEntity soldadoEntity = objectMapper.convertValue(soldado, SoldadoEntity.class);
        soldadoRepository.save(soldadoEntity);
    }

    public void alterarSoldado(Long id, SoldadoEditRequest soldadoEditRequest) {
        SoldadoEntity soldadoEntity = objectMapper.convertValue(soldadoEditRequest, SoldadoEntity.class);
        soldadoEntity.setId(id);
        soldadoRepository.save(soldadoEntity);
    }

    public void deletarSoldado(Long id) {
        SoldadoEntity soldado = soldadoRepository.findById(id).orElseThrow();
        soldadoRepository.delete(soldado);
    }

    public CollectionModel<SoldadoListResponse> buscarSoldados(){
        List<SoldadoEntity> all = soldadoRepository.findAll();
        List<SoldadoListResponse> soldadoStream = all.stream()
                .map(it -> resourceSoldado.criarLink(it))
                .collect(Collectors.toList());
        return new Resources<>(soldadoStream);
    }
}
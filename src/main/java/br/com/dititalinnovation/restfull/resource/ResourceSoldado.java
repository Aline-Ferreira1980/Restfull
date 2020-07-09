package br.com.dititalinnovation.restfull.resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.dititalinnovation.restfull.controller.SoldadoController;
import br.com.dititalinnovation.restfull.response.SoldadoListResponse;
import br.com.dititalinnovation.restfull.response.SoldadoResponse;
import br.com.dititalinnovation.restfull.entity.SoldadoEntity;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResourceSoldado {

    private ObjectMapper objectMapper;

    public ResourceSoldado(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public SoldadoListResponse criarLink(SoldadoEntity soldadoEntity){

        SoldadoListResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoListResponse.class);

        Link link = linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();

        soldadoListResponse.add(link);

        return soldadoListResponse;

    }

    public SoldadoResponse criarLinkDetalhe(SoldadoEntity soldadoEntity){

        Link link;

        SoldadoResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);

        if (soldadoEntity.getStatus().equals("Morto")){
            link = linkTo(methodOn(SoldadoController.class).deleteSoldado(soldadoEntity.getId()))
                    .withRel("remover")
                    .withTitle("Deletar soldado")
                    .withType("delete");
        } else if (soldadoEntity.getStatus().equals("Vivo")) {
            link = linkTo(methodOn(SoldadoController.class).patrulharCastelo(soldadoEntity.getId()))
                    .withRel("patrulha")
                    .withTitle("Patrulhar Castelo")
                    .withType("put");
        } else {
            link = linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();
        }

        soldadoListResponse.add(link);

        return soldadoListResponse;

    }

}
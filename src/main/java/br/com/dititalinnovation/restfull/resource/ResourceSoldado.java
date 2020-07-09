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
        SoldadoResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);

        if (soldadoEntity.getStatus().equals("Morto")){
            Link link = linkTo(methodOn(SoldadoController.class).deleteSoldado(soldadoEntity.getId()))
                    .withRel("remover")
                    .withTitle("Deletar soldado")
                    .withType("delete");
            soldadoListResponse.add(link);

        } else if (soldadoEntity.getStatus().equals("Vivo")) {
           Link link = linkTo(methodOn(SoldadoController.class).frenteCastelo(soldadoEntity.getId()))
                    .withRel("batalhar")
                    .withTitle("Ir para frente do Castelo")
                    .withType("put");
            soldadoListResponse.add(link);

        } else {
           Link link = linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();
            soldadoListResponse.add(link);
        }



        return soldadoListResponse;

    }

}
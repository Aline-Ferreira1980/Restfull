package br.com.dititalinnovation.restfull.enums;

import java.util.stream.Stream;

public enum Raca {
    HUMANO("humano"),
    ELFO("elfo"),
    ORC ("orc"),
    ANAO("anao");

    private String value;

    Raca(String value) {
         this.value= value;
    }
    public String getValue(){
        return value;
    }
    public static Raca of(String value){
       return Stream.of(Raca.values())
                .filter(it->it.equals(value))
                .findFirst()
                .orElseThrow();

    }
}

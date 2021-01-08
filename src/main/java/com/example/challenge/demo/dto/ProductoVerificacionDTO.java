package com.example.challenge.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductoVerificacionDTO {

    @JsonProperty(value="status")
    private String status;

    @JsonProperty(value="esPrioritario")
    private Boolean esPrioritario;

    @JsonProperty(value="verificado")
    private Boolean verificado;
}

package com.example.challenge.demo.service;

import com.example.challenge.demo.dto.ProductoVerificacionDTO;

import java.util.List;

public interface ProductoService {

    ProductoVerificacionDTO getProductoVerificacion(String codigo);

    List<ProductoVerificacionDTO> getProductoVerificacionMasivo(List<String> codigos);
}

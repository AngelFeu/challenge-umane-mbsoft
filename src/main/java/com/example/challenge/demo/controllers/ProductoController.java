package com.example.challenge.demo.controllers;

import com.example.challenge.demo.dto.ProductoVerificacionDTO;
import com.example.challenge.demo.service.impl.ProductoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Producto API")
@RequestMapping("api/v1/producto")
@RestController
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoServiceImpl productoService;

    @GetMapping(value = "/productoVerificacion", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Verificación exitosa"),
            @ApiResponse(code = 417, message = "No se pudo verificar el código"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    public @ResponseBody
    ResponseEntity<ProductoVerificacionDTO> getProductoVerificacion(@RequestParam(value = "codigo") String codigo) {
        ProductoVerificacionDTO productoVerificacion = new ProductoVerificacionDTO();

        try {
            productoVerificacion = productoService.getProductoVerificacion(codigo);

            if (productoVerificacion == null) {
                logger.error("Error en retorno de la verificación del código");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(productoVerificacion);
            }

            logger.info("retorno correctamente la verificación del código");
            return ResponseEntity.status(HttpStatus.OK).body(productoVerificacion);
        } catch (Exception e) {
            logger.error("Error al verificar el producto");
            productoVerificacion.setStatus("Error al verificar el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productoVerificacion);
        }
    }

    @GetMapping(value = "/productoVerificacionMasivo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Verificación exitosa"),
            @ApiResponse(code = 417, message = "No se pudo verificar el código"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    public @ResponseBody
    ResponseEntity<List<ProductoVerificacionDTO>> getProductoVerificacionMasivo(@RequestParam(value = "codigo") List<String> codigos) {
        List<ProductoVerificacionDTO> productoVerificacionMasivo = new ArrayList<>();

        try {
            productoVerificacionMasivo = productoService.getProductoVerificacionMasivo(codigos);

            if (productoVerificacionMasivo == null) {
                logger.error("Error en retorno de la verificación del código");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(productoVerificacionMasivo);
            }

            logger.info("retorno correctamente la verificación del código");
            return ResponseEntity.status(HttpStatus.OK).body(productoVerificacionMasivo);
        } catch (Exception e) {
            logger.error("Error al verificar el producto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productoVerificacionMasivo);
        }
    }
}

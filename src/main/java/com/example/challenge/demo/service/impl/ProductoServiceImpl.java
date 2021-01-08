package com.example.challenge.demo.service.impl;

import com.example.challenge.demo.controllers.ProductoController;
import com.example.challenge.demo.dto.ProductoVerificacionDTO;
import com.example.challenge.demo.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final Integer CANT_SECCIONES_COD = 3;
    private final Integer CANT_POSICIONES_COD = 3;
    private final Integer CANT_POSICIONES_NUM = 5;
    private final Integer CANT_POSICIONES_VER = 1;

    public String[] ordernarAlfabeticamente(String[] codigos) {
        Arrays.sort(codigos);
        return codigos;
    }

    public List<String> unirArrays(List<String> codigos, List<String> codigos2) {
        List<String> arr = new ArrayList<>();

        arr.addAll(codigos);
        arr.addAll(codigos2);

        return arr;
    }

    public List<String> getInterseccion(List<String> codigos, List<String> codigos2) {
        List<String> arr = new ArrayList<>();

        for (String codigo: codigos) {
            if (codigos2.contains(codigo)) {
                arr.add(codigo);
            }
        }

        return arr;
    }

    @Override
    public List<ProductoVerificacionDTO> getProductoVerificacionMasivo(List<String> codigos) {
        List<ProductoVerificacionDTO> productoVerificacionDTOList = new ArrayList<>();

        for (String codigo: codigos) {
            productoVerificacionDTOList.add(getProductoVerificacion(codigo));
        }

        return productoVerificacionDTOList;
    }

    @Override
    public ProductoVerificacionDTO getProductoVerificacion(String codigo) {
        ProductoVerificacionDTO productoVerificacion = new ProductoVerificacionDTO();

        if (!validar(codigo)) {
            productoVerificacion.setStatus("Error");
            productoVerificacion.setVerificado(false);
            return productoVerificacion;
        }

        try {
            productoVerificacion.setEsPrioritario(esPrioritario(codigo));
            productoVerificacion.setVerificado(verificar(codigo));

            if (productoVerificacion.getVerificado()) productoVerificacion.setStatus("Ok");
            else productoVerificacion.setStatus("Error");
        }catch (Exception e){
            productoVerificacion = null;
        }
        return productoVerificacion;
    }

    // boolean esPrioritario(codigo)
    // donde devuelve true si el código de producto comienza con las letras P o W;
    private Boolean esPrioritario(String codigo) {
        if (codigo.startsWith("P") || codigo.startsWith("W")) return true;
        return false;
    }

    private Boolean verificar(String codigo) {
        // spliteamos para obtener las partes del codigo
        String[] arrCodigo = codigo.split("[-]", 0);

        Integer sumarizado = sumarizado(arrCodigo[1]);
        Integer digitoVerificador = Integer.valueOf(arrCodigo[2]);

        if (sumarizado == digitoVerificador) return true;
        return false;
    }

    private Integer sumarizado(String codigo) {
        Integer sumarizado;

        sumarizado = sumarDesdeString(codigo);
        if (sumarizado >= 10) sumarizado = sumarDesdeString(sumarizado.toString());

        return sumarizado;
    }

    private Integer sumarDesdeString (String codigo) {
        Integer sumarizado = 0;
        try {
            for (Integer i = 0; i < codigo.length(); i++) {
                sumarizado = sumarizado + Integer.parseInt(String.valueOf(codigo.charAt(i)));
            }
            return sumarizado;
        } catch(Exception e) {
            return 0;
        }
    }

    private Boolean validar(String codigo) {
        // spliteamos para obtener las partes del codigo
        String[] arrCodigo = codigo.split("[-]", 0);

        if (arrCodigo.length != CANT_SECCIONES_COD) return false;

        Boolean valCod = validarString(arrCodigo[0], CANT_POSICIONES_COD);
        Boolean valNum = validarNumero(arrCodigo[1], CANT_POSICIONES_NUM);
        Boolean valVer = validarNumero(arrCodigo[2], CANT_POSICIONES_VER);

        if (!valCod || !valNum || !valVer) return false;

        return true;
    }

    private Boolean validarString(String codigo, Integer cantPosiciones) {
        if (!validarNullAndLarge(codigo, cantPosiciones)) return false;

        Pattern pat = Pattern.compile("[a-zA-Z]{"+cantPosiciones+"}");
        Matcher mat = pat.matcher(codigo);

        return mat.matches();
    }

    private Boolean validarNumero(String codigo, Integer cantPosiciones) {
        if (!validarNullAndLarge(codigo, cantPosiciones)) return false;

        Pattern pat = Pattern.compile("[0-9]{"+cantPosiciones+"}");
        Matcher mat = pat.matcher(codigo);

        return mat.matches();
    }

    private Boolean validarNullAndLarge(String codigo, Integer cantPosiciones) {
        if (codigo == null) return false;
        if (codigo.length() != cantPosiciones) return false;

        return true;
    }
}

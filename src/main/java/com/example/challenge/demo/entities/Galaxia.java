package com.example.challenge.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "GALAXIA")
public class Galaxia {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "OBSERVACION")
    private String observacion;
}

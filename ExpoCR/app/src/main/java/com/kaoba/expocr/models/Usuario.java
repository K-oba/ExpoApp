package com.kaoba.expocr.models;

/**
 * Created by Jimmi on 27/06/2017.
 */

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String rol;

    public Usuario (){
        setId(null);
        setCorreo(null);
        setNombre(null);
        setRol(null);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

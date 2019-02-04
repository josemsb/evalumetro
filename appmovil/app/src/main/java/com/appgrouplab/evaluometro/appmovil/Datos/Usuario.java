package com.appgrouplab.evaluometro.appmovil.Datos;

public class Usuario {
    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getStrRegion() {
        return strRegion;
    }

    public void setStrRegion(String strRegion) {
        this.strRegion = strRegion;
    }

    public String getStrProvincia() {
        return strProvincia;
    }

    public void setStrProvincia(String strProvincia) {
        this.strProvincia = strProvincia;
    }

    public String getStrDistrito() {
        return strDistrito;
    }

    public void setStrDistrito(String strDistrito) {
        this.strDistrito = strDistrito;
    }

    private String Correo;
    private Integer idRegion;
    private Integer idProvincia;
    private Integer idDistrito;
    private String strRegion;
    private String strProvincia;
    private String strDistrito;

}

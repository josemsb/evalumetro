package com.appgrouplab.evaluometro.appmovil.Datos;

public class Candidato {
    private String Nombre;
    private Integer Id;
    private String Partido;
    private Integer IdPartido;
    private String FotoPartido;
    private String FotoCandidato;
    private String FotoFondo;
    private Integer Votos;
    private Integer Porcentaje;
    private String TipoCandidatura;
    private String Abreviatura;

    public Integer getVotos() { return Votos;}

    public void setVotos(Integer votos) { Votos = votos;}

    public Integer getPorcentaje() { return Porcentaje;}

    public void setPorcentaje(Integer porcentaje) { Porcentaje = porcentaje;}

    public String getTipoCandidatura() {
        return TipoCandidatura;
    }

    public void setTipoCandidatura(String tipoCandidatura) {
        TipoCandidatura = tipoCandidatura;
    }

    public String getAbreviatura() {
        return Abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        Abreviatura = abreviatura;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPartido() {
        return Partido;
    }

    public void setPartido(String partido) {
        Partido = partido;
    }

    public Integer getIdPartido() {
        return IdPartido;
    }

    public void setIdPartido(Integer idPartido) {
        IdPartido = idPartido;
    }

    public String getFotoPartido() {
        return FotoPartido;
    }

    public void setFotoPartido(Integer mId) {
        FotoPartido = "http://evaluometro.pe/img/partidos/" + mId.toString() + ".jpg";
    }

    public String getFotoCandidato() {
        return FotoCandidato;
    }

    public void setFotoCandidato(Integer mId) {
        FotoCandidato = "http://evaluometro.pe/img/candidatos/" + mId.toString() + ".png";
    }

    public String getFotoFondo() {return FotoFondo;}

    public void setFotoFondo(Integer mId) {
        FotoFondo = "http://evaluometro.pe/img/fondos/" + mId.toString() + ".jpg";
    }


}

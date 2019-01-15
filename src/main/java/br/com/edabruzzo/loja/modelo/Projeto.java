/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edabruzzo.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @author Emm
 */
public class Projeto {
    
    private String nome;
    private long id;
    private int anoInicio;

    public Projeto(long id, String nome, int anoInicio) {
        this.nome = nome;
        this.id = id;
        this.anoInicio = anoInicio;
    }

    public Projeto() {
    }

    public String getNome() {
        return nome;
    }

    public long getId() {
        return id;
    }

    public int getAnoInicio() {
        return anoInicio;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    public String toXML(){
		/*
            E esse XML é exatamente o XML que o XStream retornou em nosso servidor. 
            Lembrando que se você quiser configurar o XStream ou usar JAX-B 
            ou qualquer outra biblioteca de outros media types e formatos, 
            sinta-se a vontade para em produção alterar seu código.
            */
		return new XStream().toXML(this);
	}

    public String toJSON() {
    
        return new Gson().toJson(this);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAnoInicio(int anoInicio) {
        this.anoInicio = anoInicio;
    }
    
    
    
}

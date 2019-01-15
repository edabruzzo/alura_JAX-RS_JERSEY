package br.com.edabruzzo.loja.resource;

import br.com.edabruzzo.loja.dao.ProjetoDAO;
import br.com.edabruzzo.loja.modelo.Projeto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;




@Path("projetos")
public class ProjetoResource { 

@GET
@Produces(MediaType.APPLICATION_XML)
public String busca(@QueryParam("id") long id) {
    Projeto projeto = new ProjetoDAO().busca(id);
    return projeto.toXML();
}




}

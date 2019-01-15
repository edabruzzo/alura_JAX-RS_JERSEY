package br.com.edabruzzo.loja.resource;

import br.com.edabruzzo.loja.dao.ProjetoDAO;
import br.com.edabruzzo.loja.modelo.Carrinho;
import br.com.edabruzzo.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;




@Path("projetos")
public class ProjetoResource { 

    
ProjetoDAO dao = new ProjetoDAO();
    
    
    
@Path("{id}")
@GET
@Produces(MediaType.APPLICATION_XML)
public String busca(@PathParam("id") long id) {
    Projeto projeto = dao.busca(id);
    return projeto.toXML();
}


@Path("{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public String buscaJSON(@PathParam("id") long id) {
    Projeto projeto = dao.busca(id);
    return projeto.toJSON();
}

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public String adiciona(String conteudo) {
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        dao.adiciona(projeto);
        return "<status>sucesso</status>";
    }

}

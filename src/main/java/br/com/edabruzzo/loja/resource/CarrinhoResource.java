package br.com.edabruzzo.loja.resource;

import br.com.edabruzzo.loja.dao.CarrinhoDAO;
import br.com.edabruzzo.loja.modelo.Carrinho;
import com.thoughtworks.xstream.XStream;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("carrinhos")
public class CarrinhoResource {

    
    CarrinhoDAO dao = new CarrinhoDAO();
    
    
    /*
    @Path("{id}")
    @GET
//@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public String buscaJSON(@PathParam("id") long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toJSON();
    }
*/
    @Path("{id}")
    @GET
//@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_XML)
    public String buscaXML(@PathParam("id") long id) {
        Carrinho carrinho = dao.busca(id);
        return carrinho.toXML();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public String adiciona(String conteudo) {
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        dao.adiciona(carrinho);
        return "<status>sucesso</status>";
    }

}

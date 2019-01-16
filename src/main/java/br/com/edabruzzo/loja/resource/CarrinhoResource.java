package br.com.edabruzzo.loja.resource;

import br.com.edabruzzo.loja.dao.CarrinhoDAO;
import br.com.edabruzzo.loja.modelo.Carrinho;
import br.com.edabruzzo.loja.modelo.Produto;
import com.thoughtworks.xstream.XStream;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo) {
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        dao.adiciona(carrinho);
        URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
    }

    @Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
        dao.removeProduto(id, produtoId);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response alteraQuantidadeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {

        Carrinho carrinho = (Carrinho) dao.busca(id);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.troca(produto);
        return Response.ok().build();
    }

}

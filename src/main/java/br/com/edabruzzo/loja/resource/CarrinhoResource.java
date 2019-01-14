package br.com.edabruzzo.loja.resource;

import br.com.edabruzzo.loja.dao.CarrinhoDAO;
import br.com.edabruzzo.loja.modelo.Carrinho;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;




@Path("carrinhos")
public class CarrinhoResource { 

@GET
@Produces(MediaType.APPLICATION_XML)
public String busca() {
    Carrinho carrinho = new CarrinhoDAO().busca(1l);
    return carrinho.toXML();
}




}

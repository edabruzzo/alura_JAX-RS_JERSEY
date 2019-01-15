/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.edabruzzo.loja.Servidor;
import br.com.edabruzzo.loja.modelo.Carrinho;
import br.com.edabruzzo.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Emm
 */
public class ClienteTest {
    
    Servidor servidor = new Servidor();
    HttpServer server = null;
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8080");
       
    //INICIA O SERVIDOR ANTES DE CADA TESTE
    @Before
    public void inicializaServidor(){
        
       server = servidor.inicializaServidor();
    }
    
   //PARA O SERVIDOR ANTES DE CADA TESTE
    @After
    public void paraServidor(){
       System.out.println("Parando servidor ... ");
       server.stop();
       System.out.println("Servidor parado !!!");

    }
    
    
    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

        /*
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.mocky.io");
        target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get();
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        System.out.println(conteudo);
	*/
        
        String conteudo = target.path("carrinhos").queryParam("id", 1l).request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals(carrinho.getRua(), "Rua Vergueiro 3185, 8 andar");

    }

    
     @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {

        String conteudo = target.path("/projetos").queryParam("id", 1l).request().get(String.class);
        //System.out.println(conteudo);
        //Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
        System.out.println(conteudo);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals(projeto.getNome(), "Minha loja");

    }
    
}

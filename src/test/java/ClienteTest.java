/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.com.edabruzzo.loja.modelo.Carrinho;
import com.thoughtworks.xstream.XStream;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Emm
 */
public class ClienteTest {

    HttpServer server = null;
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8080");
       
    //INICIA O SERVIDOR ANTES DE CADA TESTE
    @Before
    public void inicializaServidor(){
        
        System.out.println("Iniciando servidor ... ");
        URI uri = URI.create("http://localhost:8080/");
        //expõe tudo que estiver no pacote abaixo como recurso webservice
        ResourceConfig config = new ResourceConfig().packages("br.com.edabruzzo.loja");
        //criando o próprio servidor com aplicação desktop, sem qualquer uso de servidor de aplicação ou servlet container
        server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        System.out.println("Servidor rodando !!! ");
        //paramos o servidor quando o usuário apertar enter:
       
    }
    
   //PARA O SERVIDOR ANTES DE CADA TESTE
    @After
    public void paraServidor(){
       System.out.println("Parando servidor ... ");
       server.stop();
       System.out.println("Servidor parado !!!");

    }
    
    
    @Test
    public void testaQueAConexaoComOServidorFunciona() {

        /*
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.mocky.io");
        target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get();
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        System.out.println(conteudo);
	*/
        String conteudo = target.path("carrinhos").request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals(carrinho.getRua(), "Rua Vergueiro 3185, 8 andar");

    }

    
     @Test
    public void testaPathProjetos() {

        String conteudo = target.path("/projetos").request().get(String.class);
        System.out.println(conteudo);
        Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
        
    }
    
}

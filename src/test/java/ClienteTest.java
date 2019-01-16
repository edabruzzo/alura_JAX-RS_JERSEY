/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.edabruzzo.loja.Servidor;
import br.com.edabruzzo.loja.modelo.Carrinho;
import br.com.edabruzzo.loja.modelo.Produto;
import br.com.edabruzzo.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
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
    //ao invés de criar um cliente padrão, eu vou criar a configuração de um cliente.
    //https://cursos.alura.com.br/course/webservices-rest-com-jaxrs-e-jersey/task/4775
    //Agora que eu tenho essa configuração do meu cliente eu vou registrar uma API de log, 
    //que é o LoggingFilter.
    /*
    
     */
    Client client = this.configurarCliente();
 
    WebTarget target = client.target("http://localhost:8080");

    
    
    public Client configurarCliente() {

        ClientConfig config = new ClientConfig();
        config.register(new LoggingFilter());

        return ClientBuilder.newClient(config);

    }

    //INICIA O SERVIDOR ANTES DE CADA TESTE
    @Before
    public void inicializaServidor() {

        server = servidor.inicializaServidor();
    }

    //PARA O SERVIDOR ANTES DE CADA TESTE
    @After
    public void paraServidor() {
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
        //Teste para QueryParam que no browser seria chada desta forma:
        //localhost:8080/carrinhos?id=1L
        //String conteudo = target.path("carrinhos").queryParam("id", 1l).request().get(String.class);
        //Teste para PathParam, passando o id como uri e não como parametro
        //isto permite aproveitar o cache do browser
        String conteudo = target.path("carrinhos/1").request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals(carrinho.getRua(), "Rua Vergueiro 3185, 8 andar");

    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
        //Teste para QueryParam que no browser seria chada desta forma:
        //localhost:8080/projetos?id=1L
        //String conteudo = target.path("/projetos").queryParam("id", 1l).request().get(String.class);

        //Teste para PathParam, passando o id como uri e não como parametro
        //isto permite aproveitar o cache do browser
        String conteudo = target.path("/projetos/1").request().get(String.class);

        //System.out.println(conteudo);
        //Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
        System.out.println(conteudo);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals(projeto.getNome(), "Minha loja");

    }

    @Test
    public void testaPostCarrinho() {

        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        //Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        verificaResposta(response);
        String location = response.getHeaderString("Location");
        System.out.println(String.format("Location do post do carrinho: %s", location));
        String conteudo = target.path(location.replace("http://localhost:8080/", " ").trim()).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Tablet"));

    }

    @Test
    public void testaPostProjeto() {

        Projeto projeto = new Projeto();
        projeto.setAnoInicio(2015);
        projeto.setNome("Viagens");
        String xml = projeto.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Response response = target.path("/projetos").request().post(entity);
        //Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        this.verificaResposta(response);

    }

    private void verificaResposta(Response response) {

        System.out.println(response.toString());
        System.out.println(String.format("HEADERS: %s", response.getHeaders().toString()));
        Assert.assertEquals(201, response.getStatus());

    }

    @Test
    public void testaRemocaoProduto() {

        String location = "carrinhos/1/produtos/6237";
        Response response = target.path(location).request().delete();
        Assert.assertEquals(200, response.getStatus());
        String conteudo = target.path("carrinhos/1").request().get(String.class);
        System.out.println("CONTEÚDO QUE CHEGA APÓS DELETAR PRODUTO DO CARRINHO : " + conteudo);
        Assert.assertTrue(!conteudo.contains("6237"));

    }

    @Test
    public void testaRemocaoProjeto() {

        Response response_1 = target.path("projetos/1").request().delete();
        Assert.assertEquals(200, response_1.getStatus());
        Response response_2 = target.path("projetos/1").request().get();
        Assert.assertEquals(500, response_2.getStatus());

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Emm
 */
public class ClienteTest {

    @Test
    public void testaQueAConexaoComOServidorFunciona() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.mocky.io");
        target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get();
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        System.out.println(conteudo);
	Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));

    }

}

package br.com.edabruzzo.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

    public static void main(String[] args) throws IOException {
        URI uri = URI.create("http://localhost:8080/");
        //expõe tudo que estiver no pacote abaixo como recurso webservice
        ResourceConfig config = new ResourceConfig().packages("br.com.edabruzzo.loja");
        //criando o próprio servidor com aplicação desktop, sem qualquer uso de servidor de aplicação ou servlet container
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        System.out.println("Servidor rodando");
        //paramos o servidor quando o usuário apertar enter:
        System.in.read();
        server.stop();


    }

}

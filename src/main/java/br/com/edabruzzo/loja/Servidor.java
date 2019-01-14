package br.com.edabruzzo.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

    public static void main(String[] args) throws IOException {
        HttpServer server = inicializaServidor();
        System.in.read();
        server.stop();

    }

    public static HttpServer inicializaServidor() {

        System.out.println("Iniciando servidor ... ");
        URI uri = URI.create("http://localhost:8080/");
        //expõe tudo que estiver no pacote abaixo como recurso webservice
        ResourceConfig config = new ResourceConfig().packages("br.com.edabruzzo.loja");
        //criando o próprio servidor com aplicação desktop, sem qualquer uso de servidor de aplicação ou servlet container
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
        System.out.println("Servidor rodando !!! ");
        return server;

    }


}

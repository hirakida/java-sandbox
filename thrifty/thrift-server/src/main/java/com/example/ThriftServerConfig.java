package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThriftServerConfig {

    @Bean(destroyMethod = "stop")
    public TServer server(HelloHandler helloHandler) throws TTransportException {
        TServerSocket serverSocket = new TServerSocket(8081);
        TServer server = new TSimpleServer(new TSimpleServer.Args(serverSocket)
                                                   .processor(new Hello.Processor<>(helloHandler))
                                                   .protocolFactory(protocolFactory()));

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(server::serve);
        return server;
    }

    @Bean
    public TProtocolFactory protocolFactory() {
        return new TBinaryProtocol.Factory();
    }
}

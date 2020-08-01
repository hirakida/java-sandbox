package com.example;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.microsoft.thrifty.protocol.BinaryProtocol;
import com.microsoft.thrifty.protocol.Protocol;
import com.microsoft.thrifty.service.AsyncClientBase;
import com.microsoft.thrifty.service.ServiceMethodCallback;
import com.microsoft.thrifty.transport.SocketTransport;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineRunnerImpl.class);

    @Override
    public void run(String... args) throws Exception {
        SocketTransport transport = new SocketTransport.Builder("localhost", 8081).build();
        transport.connect();
        Protocol protocol = new BinaryProtocol(transport);
        HelloClient client = new HelloClient(protocol, new AsyncClientListenerImpl());

        client.hello(new ServiceMethodCallbackImpl());

        TimeUnit.MILLISECONDS.sleep(100);
        client.close();
    }

    private static class AsyncClientListenerImpl implements AsyncClientBase.Listener {
        @Override
        public void onTransportClosed() {
            LOGGER.info("transportClosed");
        }

        @Override
        public void onError(Throwable error) {
            LOGGER.error("{}", error.getMessage(), error);
        }
    }

    private static class ServiceMethodCallbackImpl implements ServiceMethodCallback<String> {
        @Override
        public void onSuccess(String response) {
            LOGGER.info("result={}", response);
        }

        @Override
        public void onError(Throwable error) {
            LOGGER.error("{}", error.getMessage(), error);
        }
    }
}

package com.example;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jackson.JacksonSupport;
import io.helidon.metrics.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

public class Main {

    public static void main(String[] args) {
        LogConfig.configureRuntime();
        Config config = Config.create();

        WebServer server = WebServer.builder(createRouting())
                                    .config(config.get("server"))
                                    .addMediaSupport(JacksonSupport.create())
                                    .build();
        server.start()
              .thenAccept(ws -> {
                  ws.whenShutdown()
                    .thenRun(() -> System.out.println("WEB server is DOWN. Good bye!"));
              })
              .exceptionally(t -> {
                  System.err.println("Startup failed: " + t.getMessage());
                  t.printStackTrace(System.err);
                  return null;
              });
    }

    private static Routing createRouting() {
        MetricsSupport metrics = MetricsSupport.create();
        HealthSupport health = HealthSupport.builder()
                                            .addLiveness(HealthChecks.healthChecks())
                                            .build();
        PersonService personService = new PersonService();

        return Routing.builder()
                      .register(metrics)    // /health
                      .register(health)     // /metrics
                      .register("/persons", personService)
                      .get("/", (req, res) -> res.send("Hello Helidon!"))
                      .build();
    }
}

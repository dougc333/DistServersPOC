package com.example.distservers.worker;

import org.skife.config.Config;
import org.skife.config.Default;

public interface WorkerConfig {

    @Config("example.worker.http-server.ip")
    @Default("0.0.0.0")
    String getHttpServerIp();

    @Config("example.worker.http-server.port")
    @Default("8080")
    int getHttpServerPort();
}

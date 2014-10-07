package com.example.distservers.controller.svc;

import com.google.inject.AbstractModule;
import com.example.distservers.controller.ControllerCoreModule;
import com.example.distservers.controller.zookeeper.ZKServerModule;
import com.example.distservers.curator.InstanceSerializerModule;
import com.example.distservers.http.server.DefaultJerseyServletModule;
import com.example.distservers.ipc.IpcJsonModule;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.job.task.TaskPluginRegistryModule;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.task.cassandra.CassandraModule;
import com.example.distservers.task.hbase.HbaseModule;
import com.example.distservers.task.hbaseAsync.HbaseAsyncModule;
//import com.example.distservers.task.mongodb.MongoDbModule;
import com.example.distservers.zookeeper.CuratorModule;
import com.palominolabs.config.ConfigModule;
import com.palominolabs.config.ConfigModuleBuilder;
import com.example.http.server.HttpServerModule;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricsRegistry;
import org.apache.commons.configuration.SystemConfiguration;

public final class ControllerMainModule extends AbstractModule {
    @Override
    protected void configure() {
        binder().requireExplicitBindings();
        bind(ControllerMain.class);

        install(new HttpServerModule());

        bind(MetricsRegistry.class).toInstance(Metrics.defaultRegistry());

        install(new DefaultJerseyServletModule());

        install(new ControllerCoreModule());
        install(new ConfigModuleBuilder().addConfiguration(new SystemConfiguration()).build());

        install(new CuratorModule());

        install(new InstanceSerializerModule());

        install(new IpcJsonModule());

        ConfigModule.bindConfigBean(binder(), ControllerConfig.class);

        install(new ZKServerModule());

        install(new KeyGeneratorFactoryFactoryRegistryModule());
        install(new ValueGeneratorFactoryFactoryRegistryModule());
        install(new TaskPluginRegistryModule());

        install(new HbaseAsyncModule());
        install(new HbaseModule());
        install(new CassandraModule());
       // install(new MongoDbModule());
    }
}

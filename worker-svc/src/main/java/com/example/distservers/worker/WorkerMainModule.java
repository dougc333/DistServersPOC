package com.example.distservers.worker;

import com.google.inject.AbstractModule;
import com.example.distservers.curator.InstanceSerializerModule;
import com.example.distservers.http.server.DefaultJerseyServletModule;
import com.example.distservers.ipc.IpcHttpClientModule;
import com.example.distservers.ipc.IpcJsonModule;
import com.example.distservers.job.key.DefaultKeyGeneratorFactoriesModule;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.job.registry.JobRegistryModule;
import com.example.distservers.job.task.TaskPluginRegistryModule;
import com.example.distservers.job.value.DefaultValueGeneratorFactoryFactoriesModule;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.task.cassandra.CassandraModule;
import com.example.distservers.task.hbase.HbaseModule;
import com.example.distservers.task.hbaseAsync.HbaseAsyncModule;
//import com.example.distservers.task.mongodb.MongoDbModule;
import com.example.distservers.task.reporting.TaskProgressClientModule;
import com.example.distservers.worker.http.WorkerResourceModule;
import com.example.distservers.zookeeper.CuratorModule;
import com.palominolabs.config.ConfigModule;
import com.palominolabs.config.ConfigModuleBuilder;
import com.example.http.server.HttpServerModule;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.MetricsRegistry;
import org.apache.commons.configuration.SystemConfiguration;

public final class WorkerMainModule extends AbstractModule {
    @Override
    protected void configure() {
        binder().requireExplicitBindings();
        bind(WorkerMain.class);

        install(new HttpServerModule());

        bind(MetricsRegistry.class).toInstance(Metrics.defaultRegistry());

        install(new DefaultJerseyServletModule());

        install(new WorkerResourceModule());
        install(new ConfigModuleBuilder().addConfiguration(new SystemConfiguration()).build());

        install(new CuratorModule());

        install(new InstanceSerializerModule());

        install(new IpcHttpClientModule());
        install(new IpcJsonModule());
        install(new TaskProgressClientModule());
        install(new JobRegistryModule());
        install(new QueueProviderModule());

        bind(PartitionRunner.class);

        ConfigModule.bindConfigBean(binder(), WorkerConfig.class);

        install(new KeyGeneratorFactoryFactoryRegistryModule());
        install(new ValueGeneratorFactoryFactoryRegistryModule());
        install(new DefaultKeyGeneratorFactoriesModule());
        install(new DefaultValueGeneratorFactoryFactoriesModule());

        install(new TaskPluginRegistryModule());
        install(new HbaseAsyncModule());
        install(new HbaseModule());
        install(new CassandraModule());
        //install(new MongoDbModule());
    }

}

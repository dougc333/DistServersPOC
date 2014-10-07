package com.example.distservers.worker;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.example.distservers.job.key.DefaultKeyGeneratorFactoriesModule;
import com.example.distservers.job.key.KeyGeneratorFactoryFactory;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.job.registry.JobRegistryModule;
import com.example.distservers.job.task.TaskPluginRegistry;
import com.example.distservers.job.task.TaskPluginRegistryModule;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.value.DefaultValueGeneratorFactoryFactoriesModule;
import com.example.distservers.job.value.ValueGeneratorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.task.cassandra.CassandraModule;
import com.example.distservers.task.hbase.HbaseModule;
import com.example.distservers.task.hbaseAsync.HbaseAsyncModule;
import com.example.distservers.task.reporting.NoOpTaskProgressClient;
import com.example.distservers.task.reporting.TaskProgressClient;
import org.apache.commons.configuration.MapConfiguration;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogManager;

/**
 * Just a quick hack for testing stuff out.
 */
final class HbaseMain {

    private final TaskPluginRegistry registry;

    private final TaskProgressClient taskProgressClient;

    private final KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry;
    private final ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry;

    @Inject
    HbaseMain(TaskPluginRegistry registry, TaskProgressClient taskProgressClient,
        KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry) {
        this.registry = registry;
        this.taskProgressClient = taskProgressClient;
        this.keyGeneratorFactoryFactoryRegistry = keyGeneratorFactoryFactoryRegistry;
        this.valueGeneratorFactoryFactoryRegistry = valueGeneratorFactoryFactoryRegistry;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                binder().requireExplicitBindings();
                bind(TaskProgressClient.class).to(NoOpTaskProgressClient.class);
                bind(HbaseMain.class);
            }
        }, new JobRegistryModule(), new HbaseModule(), new CassandraModule(), new HbaseAsyncModule(),
            new TaskPluginRegistryModule(), new KeyGeneratorFactoryFactoryRegistryModule(),
            new DefaultKeyGeneratorFactoriesModule(), new ValueGeneratorFactoryFactoryRegistryModule(),
            new DefaultValueGeneratorFactoryFactoriesModule());

        injector.getInstance(HbaseMain.class).run();
    }

    private void run() throws IOException {
        String tbl = "test-tbl";
        String columnFamily = "cf1";
        String qualifier = "qual";

        Map<String, Object> cassandraConfig = Maps.newHashMap();
        cassandraConfig.put("cluster", "cowpunk standalone cluster");
        cassandraConfig.put("keyspace", "test_ks");
        cassandraConfig.put("port", 9160);
        cassandraConfig.put("seeds", "127.0.1");
        cassandraConfig.put("columnFamily", "cf1");
        cassandraConfig.put("column", "col1");

//        HbaseTaskFactory hbaseTaskFactory = new HbaseTaskFactory("127.0.0.1", 2181, tbl, columnFamily, qualifier, true,
//            null);
//
//        HbaseAsyncTaskFactory hbaseAsyncTaskFactory = new HbaseAsyncTaskFactory("127.0.0.1:2181", tbl, columnFamily,
//            qualifier);    }

//        TaskFactory taskFactory = registry.get("CASSANDRA").getTaskFactory(new MapConfiguration(cassandraConfig));
//
//        go(taskFactory);
    }

    private void go(TaskFactory taskFactory) throws IOException {
        KeyGeneratorFactoryFactory keyGenerator = keyGeneratorFactoryFactoryRegistry.get("WORKER_ID_THREAD_ID_COUNTER");
        HashMap<String, Object> valueConfig = Maps.newHashMap();
        valueConfig.put("size", 100);
        ValueGeneratorFactory valueGenerator = valueGeneratorFactoryFactoryRegistry.get("ZERO_BYTE_ARRAY").getFactory(
            new MapConfiguration(valueConfig));

//        Collection<Runnable> runnables = taskFactory
//            .getRunnables(keyGenerator.getKeyGeneratorFactory(), valueGenerator, TaskOperation.WRITE, 1, 100, 10,
//                UUID.randomUUID(), 1,
//                taskProgressClient,
//                UUID.randomUUID(), 100, new AtomicInteger());
//
//        for (Runnable runnable : runnables) {
//            runnable.run();
//        }

        taskFactory.shutdown();
    }
}

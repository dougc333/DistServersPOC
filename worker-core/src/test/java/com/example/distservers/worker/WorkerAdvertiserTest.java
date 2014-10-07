package com.example.distservers.worker;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.Closeables;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.example.distservers.config.ZookeeperConfig;
import com.example.distservers.curator.InstanceSerializerFactory;
import com.example.distservers.curator.InstanceSerializerModule;
import com.example.distservers.http.server.DefaultJerseyServletModule;
import com.example.distservers.ipc.IpcJsonModule;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.job.registry.JobRegistryModule;
import com.example.distservers.job.task.TaskPluginRegistryModule;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistryModule;
import com.example.distservers.task.reporting.NoOpTaskProgressClient;
import com.example.distservers.task.reporting.TaskProgressClient;
import com.example.distservers.worker.http.WorkerResourceModule;
import com.example.distservers.zookeeper.CuratorModule;
import com.example.http.server.HttpServerModule;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.test.TestingServer;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public final class WorkerAdvertiserTest {
    @Inject
    private WorkerAdvertiser workerAdvertiser;
    @Inject
    private ZookeeperConfig zookeeperConfig;
    @Inject
    private CuratorFramework curatorFramework;
    @Inject
    private CuratorModule.CuratorLifecycleHook curatorLifecycleHook;
    @Inject
    private GuiceFilter guiceFilter;

    private TestingServer testingServer;
    private ServiceDiscovery<WorkerMetadata> serviceDiscovery;

    @Before
    public void setUp() throws Exception {
        testingServer = new TestingServer();

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                binder().requireExplicitBindings();
                install(new TestConfigModule(testingServer.getPort()));

                install(new HttpServerModule());

                bind(MetricRegistry.class).toInstance(new MetricRegistry());

                install(new DefaultJerseyServletModule());
                install(new WorkerResourceModule());
                install(new CuratorModule());
                install(new QueueProviderModule());

                install(new InstanceSerializerModule());

                bind(PartitionRunner.class);
                install(new JobRegistryModule());
                bind(TaskProgressClient.class).to(NoOpTaskProgressClient.class);

                install(new TaskPluginRegistryModule());
                install(new ValueGeneratorFactoryFactoryRegistryModule());
                install(new KeyGeneratorFactoryFactoryRegistryModule());
                install(new IpcJsonModule());
            }
        });

        injector.injectMembers(this);

        curatorLifecycleHook.start();

        serviceDiscovery = ServiceDiscoveryBuilder.builder(WorkerMetadata.class)
            .basePath(zookeeperConfig.getBasePath())
            .client(curatorFramework)
            .serializer(injector.getInstance(InstanceSerializerFactory.class)
                .getInstanceSerializer(new TypeReference<ServiceInstance<WorkerMetadata>>() {}))
            .build();
        serviceDiscovery.start();
    }

    @After
    public void tearDown() throws Exception {
        workerAdvertiser = null;
        Closeables.closeQuietly(curatorFramework);
        Closeables.closeQuietly(serviceDiscovery);
        Closeables.closeQuietly(testingServer);

        guiceFilter.destroy();
    }

    @Test
    public void testAdvertiseAvailability() throws Exception {
        workerAdvertiser.initListenInfo("127.0.0.1", 12345);
        workerAdvertiser.advertiseAvailability();
        Collection<ServiceInstance<WorkerMetadata>> instances =
            serviceDiscovery.queryForInstances(zookeeperConfig.getWorkerServiceName());
        assertEquals(1, instances.size());

        WorkerMetadata workerMetadata = instances.iterator().next().getPayload();

        assertEquals(workerAdvertiser.getWorkerId(), workerMetadata.getWorkerId());

    }

    @Test
    public void testDeAdvertiseAvailability() throws Exception {
        workerAdvertiser.initListenInfo("127.0.0.1", 12345);
        workerAdvertiser.advertiseAvailability();
        Collection<ServiceInstance<WorkerMetadata>> instances =
            serviceDiscovery.queryForInstances(zookeeperConfig.getWorkerServiceName());
        assertEquals(1, instances.size());

        workerAdvertiser.deAdvertiseAvailability();
        instances = serviceDiscovery.queryForInstances(zookeeperConfig.getWorkerServiceName());
        assertEquals(0, instances.size());
    }
}

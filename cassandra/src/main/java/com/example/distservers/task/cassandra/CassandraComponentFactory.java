package com.example.distservers.task.cassandra;

import com.example.distservers.job.base.task.ComponentFactoryBase;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.task.TaskOutputProcessorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class CassandraComponentFactory extends ComponentFactoryBase {

    private final CassandraConfig config;

    CassandraComponentFactory(
        KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry, CassandraConfig config) {
        super(keyGeneratorFactoryFactoryRegistry, valueGeneratorFactoryFactoryRegistry);
        this.config = config;
    }

    @Nonnull
    @Override
    public TaskFactory getTaskFactory() {
        CassandraConfig c = config;

        return new CassandraTaskFactory(c.getTaskOperation(), getValueGeneratorFactory(c), c.getBatchSize(),
            getKeyGeneratorFactory(c), c.getNumQuanta(), c.getNumThreads(), c.getCluster(), c.getKeyspace(),
            c.getPort(), c.getSeeds(), c.getColumnFamily(), c.getColumn());
    }

    @Nullable
    @Override
    public TaskOutputProcessorFactory getTaskOutputProcessorFactory() {
        return null;
    }
}

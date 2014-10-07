package com.example.distservers.task.hbase;

import com.example.distservers.job.base.task.ComponentFactoryBase;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.task.TaskOutputProcessorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class HbaseComponentFactory extends ComponentFactoryBase implements ComponentFactory {

    private final HBaseConfig config;

    HbaseComponentFactory(
        KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry, HBaseConfig config) {
        super(keyGeneratorFactoryFactoryRegistry, valueGeneratorFactoryFactoryRegistry);
        this.config = config;
    }

    @Nonnull
    @Override
    public TaskFactory getTaskFactory() {
        return new HbaseTaskFactory(
            config.getTable(), config.getZkPort(), config.getZkQuorum(), config.getColumnFamily(),
            config.getQualifier(), config.isAutoFlush(), config.getWriteBufferSize(), getValueGeneratorFactory(config),
            getKeyGeneratorFactory(config), config.getTaskOperation(), config.getNumThreads(), config.getNumQuanta(),
            config.getBatchSize());
    }

    @Nullable
    @Override
    public TaskOutputProcessorFactory getTaskOutputProcessorFactory() {
        return null;
    }
}

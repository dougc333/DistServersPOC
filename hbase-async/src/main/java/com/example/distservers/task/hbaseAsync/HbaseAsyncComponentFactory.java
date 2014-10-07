package com.example.distservers.task.hbaseAsync;

import com.example.distservers.job.base.task.ComponentFactoryBase;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.task.TaskOutputProcessorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class HbaseAsyncComponentFactory extends ComponentFactoryBase implements ComponentFactory {

    private final HbaseAsyncConfig config;

    HbaseAsyncComponentFactory(KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry, HbaseAsyncConfig config) {
        super(keyGeneratorFactoryFactoryRegistry, valueGeneratorFactoryFactoryRegistry);
        this.config = config;
    }

    @Nonnull
    @Override
    public TaskFactory getTaskFactory() {
        return new HbaseAsyncTaskFactory(
            config.getTaskOperation(), getValueGeneratorFactory(config), config.getBatchSize(),
            getKeyGeneratorFactory(config), config.getNumQuanta(), config.getNumThreads(), config.getColumnFamily(),
            config.getZkQuorum(), config.getTable(), config.getQualifier());
    }

    @Nullable
    @Override
    public TaskOutputProcessorFactory getTaskOutputProcessorFactory() {
        return null;
    }
}

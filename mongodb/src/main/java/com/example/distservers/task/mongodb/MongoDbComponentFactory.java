package com.example.distservers.task.mongodb;

import com.example.distservers.job.base.task.ComponentFactoryBase;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.task.TaskOutputProcessorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class MongoDbComponentFactory extends ComponentFactoryBase implements ComponentFactory {

    private final MongoDbConfig config;

    MongoDbComponentFactory(
        KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry, MongoDbConfig config) {
        super(keyGeneratorFactoryFactoryRegistry, valueGeneratorFactoryFactoryRegistry);
        this.config = config;
    }

    @Nonnull
    @Override
    public TaskFactory getTaskFactory() {
        return new MongoDbTaskFactory(config.getTaskOperation(), getValueGeneratorFactory(config),
            config.getBatchSize(), getKeyGeneratorFactory(config), config.getNumQuanta(), config.getNumThreads(),
            config.getHostname(), config.getPort(), config.getDbName(), config.getCollectionName());
    }

    @Nullable
    @Override
    public TaskOutputProcessorFactory getTaskOutputProcessorFactory() {
        return null;
    }
}

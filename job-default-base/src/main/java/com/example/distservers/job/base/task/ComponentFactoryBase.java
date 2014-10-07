package com.example.distservers.job.base.task;

import com.example.distservers.job.key.KeyGeneratorFactory;
import com.example.distservers.job.key.KeyGeneratorFactoryFactoryRegistry;
import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.value.ValueGeneratorFactory;
import com.example.distservers.job.value.ValueGeneratorFactoryFactoryRegistry;

public abstract class ComponentFactoryBase implements ComponentFactory {

    private final KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry;
    private final ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry;

    protected ComponentFactoryBase(KeyGeneratorFactoryFactoryRegistry keyGeneratorFactoryFactoryRegistry,
        ValueGeneratorFactoryFactoryRegistry valueGeneratorFactoryFactoryRegistry) {
        this.keyGeneratorFactoryFactoryRegistry = keyGeneratorFactoryFactoryRegistry;
        this.valueGeneratorFactoryFactoryRegistry = valueGeneratorFactoryFactoryRegistry;
    }

    protected KeyGeneratorFactory getKeyGeneratorFactory(TaskConfigBase config) {
        return keyGeneratorFactoryFactoryRegistry.get(config.getKeyGen().keyGenType)
            .getKeyGeneratorFactory(config.getKeyGen().getConfig());
    }

    protected ValueGeneratorFactory getValueGeneratorFactory(TaskConfigBase config) {
        return valueGeneratorFactoryFactoryRegistry.get(config.getValueGen().getValueGenType())
            .getFactory(config.getValueGen().getConfig());
    }
}

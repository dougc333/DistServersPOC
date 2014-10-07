package com.example.distservers.job.key;

import com.google.inject.Inject;
import com.example.distservers.job.id.IdRegistry;

import java.util.Set;

public final class KeyGeneratorFactoryFactoryRegistry extends IdRegistry<KeyGeneratorFactoryFactory> {

    @Inject
    KeyGeneratorFactoryFactoryRegistry(Set<KeyGeneratorFactoryFactory> instances) {
        super(instances);
    }
}

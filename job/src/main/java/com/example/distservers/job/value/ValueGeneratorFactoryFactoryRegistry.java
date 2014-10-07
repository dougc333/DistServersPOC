package com.example.distservers.job.value;

import com.google.inject.Inject;
import com.example.distservers.job.id.IdRegistry;

import java.util.Set;

public final class ValueGeneratorFactoryFactoryRegistry extends IdRegistry<ValueGeneratorFactoryFactory> {

    @Inject
    ValueGeneratorFactoryFactoryRegistry(Set<ValueGeneratorFactoryFactory> instances) {
        super(instances);
    }
}

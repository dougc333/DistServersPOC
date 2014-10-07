package com.example.distservers.curator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.inject.Inject;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;

/**
 * Class w/o generics to allow safe Guice binding (no unchecked warnings) that can still create generified
 * InstanceSerializer instances
 */
public class InstanceSerializerFactory {

    private final ObjectReader objectReader;
    private final ObjectWriter objectWriter;

    @Inject
    InstanceSerializerFactory(@Curator ObjectReader objectReader, @Curator ObjectWriter objectWriter) {
        this.objectReader = objectReader;
        this.objectWriter = objectWriter;
    }

    public <T> InstanceSerializer<T> getInstanceSerializer(TypeReference<ServiceInstance<T>> typeReference) {
        return new JacksonInstanceSerializer<T>(objectReader, objectWriter, typeReference);
    }

}

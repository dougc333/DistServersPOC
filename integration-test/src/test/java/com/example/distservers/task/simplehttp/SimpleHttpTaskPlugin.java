package com.example.distservers.task.simplehttp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.distservers.job.id.Id;
import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.task.ControllerComponentFactory;
import com.example.distservers.job.task.TaskPartitioner;
import com.example.distservers.job.task.TaskPlugin;

import javax.annotation.Nonnull;
import java.io.IOException;

@Id(SimpleHttpTaskPlugin.TASK_TYPE)
public final class SimpleHttpTaskPlugin implements TaskPlugin {

    public static final String TASK_TYPE = "simple-http";

    @Nonnull
    @Override
    public ComponentFactory getComponentFactory(ObjectReader objectReader, JsonNode configNode) throws IOException {
        ObjectNode obj = objectReader.withType(ObjectNode.class).readValue(configNode);
        String url = obj.get("url").textValue();

        return new SimpleHttpComponentFactory(url);
    }

    @Nonnull
    @Override
    public ControllerComponentFactory getControllerComponentFactory(ObjectReader objectReader,
        JsonNode configNode) throws IOException {
        return new ControllerComponentFactory() {
            @Nonnull
            @Override
            public TaskPartitioner getTaskPartitioner() {
                return new SimpleHttpTaskPartitioner();
            }
        };
    }
}

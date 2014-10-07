package com.example.distservers.job.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.example.distservers.job.id.Id;
import com.example.distservers.job.task.ComponentFactory;

public final class Task {
    private final String taskType;

    private final JsonNode configNode;

    @JsonCreator
    public Task(@JsonProperty("type") String taskType, @JsonProperty("config") JsonNode configNode) {
        this.taskType = taskType;
        this.configNode = configNode;
    }

    /**
     * Don't mess with this JsonNode; just read from it.
     *
     * @return json node representing the config data. {@link ComponentFactory} implementations should deserialize as
     *         they see fit.
     */
    @JsonProperty("config")
    public JsonNode getConfigNode() {
        return this.configNode;
    }

    /**
     * Should match the {@link Id} annotation on a {@link ComponentFactory}
     *
     * @return the task type
     */
    @JsonProperty("type")
    public String getTaskType() {
        return taskType;
    }
}

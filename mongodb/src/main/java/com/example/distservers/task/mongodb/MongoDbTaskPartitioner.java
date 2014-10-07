package com.example.distservers.task.mongodb;

import com.example.distservers.job.base.task.TaskConfigBase;
import com.example.distservers.job.base.task.TaskPartitionerBase;

import javax.annotation.Nonnull;

final class MongoDbTaskPartitioner extends TaskPartitionerBase {

    private final MongoDbConfig config;

    MongoDbTaskPartitioner(MongoDbConfig config) {
        this.config = config;
    }

    @Nonnull
    @Override
    protected TaskConfigBase getConfig() {
        return config;
    }

    @Nonnull
    @Override
    protected String getTaskType() {
        return MongoDbTaskPlugin.TASK_TYPE;
    }
}

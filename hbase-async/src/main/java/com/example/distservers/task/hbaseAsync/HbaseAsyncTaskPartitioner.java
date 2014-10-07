package com.example.distservers.task.hbaseAsync;

import com.example.distservers.job.base.task.TaskConfigBase;
import com.example.distservers.job.base.task.TaskPartitionerBase;

import javax.annotation.Nonnull;

final class HbaseAsyncTaskPartitioner extends TaskPartitionerBase {
    private final HbaseAsyncConfig config;

    HbaseAsyncTaskPartitioner(HbaseAsyncConfig config) {
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
        return HbaseAsyncTaskPlugin.TASK_TYPE;
    }
}

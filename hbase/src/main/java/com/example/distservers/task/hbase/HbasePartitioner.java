package com.example.distservers.task.hbase;

import com.example.distservers.job.base.task.TaskConfigBase;
import com.example.distservers.job.base.task.TaskPartitionerBase;

import javax.annotation.Nonnull;

final class HbasePartitioner extends TaskPartitionerBase {
    private final HBaseConfig config;

    HbasePartitioner(HBaseConfig config) {
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
        return HbaseTaskPlugin.TASK_TYPE;
    }
}

package com.example.distservers.task.simplehttp;

import com.example.distservers.job.task.ComponentFactory;
import com.example.distservers.job.task.TaskFactory;
import com.example.distservers.job.task.TaskOutputProcessor;
import com.example.distservers.job.task.TaskOutputProcessorFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class SimpleHttpComponentFactory implements ComponentFactory {

    private final String url;

    SimpleHttpComponentFactory(String url) {
        this.url = url;
    }

    @Nonnull
    @Override
    public TaskFactory getTaskFactory() {
        return new SimpleHttpTaskFactory(url);
    }

    @Nullable
    @Override
    public TaskOutputProcessorFactory getTaskOutputProcessorFactory() {
        return new TaskOutputProcessorFactory() {
            @Nonnull
            @Override
            public TaskOutputProcessor getTaskOutputProcessor() {
                return SimpleHttpTaskOutputProcessor.INSTANCE;
            }
        };
    }
}

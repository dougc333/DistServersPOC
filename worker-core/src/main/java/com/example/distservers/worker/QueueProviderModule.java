package com.example.distservers.worker;

import com.google.inject.AbstractModule;
import com.example.distservers.job.task.TaskOutputQueueProvider;

public final class QueueProviderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaskOutputQueueProvider.class).to(DefaultTaskOutputQueueProvider.class);
    }
}

package com.example.distservers.job.task;

import com.google.inject.Inject;
import com.example.distservers.job.id.IdRegistry;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Set;

@ThreadSafe
public final class TaskPluginRegistry extends IdRegistry<TaskPlugin> {
    @Inject
    TaskPluginRegistry(Set<TaskPlugin> componentFactories) {
        super(componentFactories);
    }
}

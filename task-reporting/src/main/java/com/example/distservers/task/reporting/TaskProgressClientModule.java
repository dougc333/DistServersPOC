package com.example.distservers.task.reporting;

import com.google.inject.AbstractModule;

public final class TaskProgressClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TaskProgressClient.class).to(AsyncHttpTaskProgressClient.class);
    }
}

package com.example.distservers.task.simplehttp;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.example.distservers.job.task.TaskPlugin;

public final class SimpleHttpTaskModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), TaskPlugin.class).addBinding().to(SimpleHttpTaskPlugin.class);
    }
}

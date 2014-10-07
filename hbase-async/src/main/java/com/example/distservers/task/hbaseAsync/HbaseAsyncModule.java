package com.example.distservers.task.hbaseAsync;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.example.distservers.job.task.TaskPlugin;

public final class HbaseAsyncModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), TaskPlugin.class).addBinding().to(HbaseAsyncTaskPlugin.class);
    }
}

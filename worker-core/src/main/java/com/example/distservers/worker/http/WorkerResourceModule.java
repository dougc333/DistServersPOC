package com.example.distservers.worker.http;

import com.google.inject.AbstractModule;
import com.example.distservers.worker.WorkerAdvertiser;

public final class WorkerResourceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JobResource.class);
        bind(ControlResource.class);
        bind(WorkerAdvertiser.class);
    }
}

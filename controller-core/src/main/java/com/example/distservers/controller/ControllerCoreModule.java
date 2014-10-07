package com.example.distservers.controller;

import com.google.inject.AbstractModule;
import com.example.distservers.controller.http.JobResource;
import com.example.distservers.worker.WorkerControlFactory;
import com.example.distservers.worker.WorkerFinder;

public final class ControllerCoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WorkerFinder.class);
        bind(WorkerControlFactory.class);
        bind(JobFarmer.class);
        bind(JobResource.class);
    }
}

package com.example.distservers.job.task;

import javax.annotation.Nonnull;

/**
 * Provides components used by the controller.
 */
public interface ControllerComponentFactory {

    @Nonnull
    TaskPartitioner getTaskPartitioner();
}

package com.example.distservers.job.base.task;

import com.example.distservers.job.key.KeyGeneratorFactory;
import com.example.distservers.job.task.TaskOperation;
import com.example.distservers.job.value.ValueGeneratorFactory;

/**
 * Convenience base class for the default task factories.
 */
public abstract class TaskFactoryBase {
    protected final ValueGeneratorFactory valueGeneratorFactory;
    protected final KeyGeneratorFactory keyGeneratorFactory;
    protected final TaskOperation taskOperation;
    protected final int numThreads;
    protected final int numQuanta;
    protected final int batchSize;

    protected TaskFactoryBase(TaskOperation taskOperation, ValueGeneratorFactory valueGeneratorFactory, int batchSize,
        KeyGeneratorFactory keyGeneratorFactory, int numQuanta, int numThreads) {
        this.taskOperation = taskOperation;
        this.valueGeneratorFactory = valueGeneratorFactory;
        this.batchSize = batchSize;
        this.keyGeneratorFactory = keyGeneratorFactory;
        this.numQuanta = numQuanta;
        this.numThreads = numThreads;
    }

}

package com.example.distservers.task.cassandra;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.example.distservers.job.key.KeyGenerator;
import com.example.distservers.job.task.TaskOperation;
import com.example.distservers.job.value.ValueGenerator;
import com.example.distservers.job.base.task.AbstractTaskRunnable;
import com.example.distservers.task.reporting.TaskProgressClient;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

final class CassandraRunnable extends AbstractTaskRunnable {

    private final Keyspace keyspace;
    private final ColumnFamily<byte[], byte[]> columnFamily;
    private final byte[] columnName;


    private MutationBatch batch;

    CassandraRunnable(TaskOperation taskOperation, KeyGenerator keyGenerator, UUID workerId, int partitionId, int numQuanta,
        int batchSize, UUID jobId, ValueGenerator valueGenerator, Keyspace keyspace,
        ColumnFamily<byte[], byte[]> columnFamily, byte[] columnName) {
        super(taskOperation, keyGenerator, workerId, partitionId, numQuanta, batchSize, jobId, valueGenerator);
        this.keyspace = keyspace;
        this.columnFamily = columnFamily;
        this.columnName = columnName;
    }

    @Override
    protected void onBatchStart() {
        switch (getTaskOperation()) {

        case WRITE:
            batch = keyspace.prepareMutationBatch();
            break;

        default:
            break;

        }
    }

    @Override
    protected void onQuanta(byte[] keyBytes, byte[] valueBytes) {
        switch (getTaskOperation()) {

        case WRITE:
            batch.withRow(columnFamily, keyBytes).putColumn(columnName, valueBytes, null);
            break;

        case READ:
            // TODO
            break;

        }
    }

    @Override
    protected void onBatchCompletion() throws IOException {
        switch (getTaskOperation()) {

        case WRITE:
            try {
                batch.execute();
            } catch (ConnectionException e) {
                throw new IOException(e);
            }
            break;

        default:
          break;

        }
    }

    @Override
    protected void onCompletion() throws IOException {
        // no op
    }
}

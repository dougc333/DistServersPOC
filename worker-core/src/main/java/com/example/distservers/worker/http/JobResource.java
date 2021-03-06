package com.example.distservers.worker.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.example.distservers.job.json.Partition;
import com.example.distservers.worker.PartitionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Receives partitions (the per-worker slice of a job) sent by the controller.
 */
@Path("worker/job")
@Singleton
public final class JobResource {

    private static final Logger logger = LoggerFactory.getLogger(JobResource.class);

    private final PartitionRunner partitionRunner;

    @Inject
    JobResource(PartitionRunner partitionRunner) {
        this.partitionRunner = partitionRunner;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{jobId}/partition")
    public Response submit(@PathParam("jobId") UUID jobId, Partition partition) {
        logger.info("Processing job submission <" + jobId + "> partition <" + partition.getPartitionId() + ">");

        if (!partitionRunner.runPartition(partition)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.ACCEPTED).build();
    }

}

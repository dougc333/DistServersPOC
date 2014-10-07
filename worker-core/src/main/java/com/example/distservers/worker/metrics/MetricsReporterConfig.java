package com.example.distservers.worker.metrics;

import org.skife.config.Config;
import org.skife.config.Default;

public interface MetricsReporterConfig {

    @Config("example.metrics.reporter.connection-string")
    @Default("")
    public String getConnectionString();

    @Config("example.metrics.reporter.interval")
    @Default("1")
    public int getInterval();

    @Config("example.metrics.reporter.interval.units")
    @Default("SECONDS")
    public String getIntervalTimeUnit();

    @Config("example.metrics.reporter.durations.units")
    @Default("MILLISECONDS")
    public String getDurationsTimeUnit();

    @Config("example.metrics.reporter.rates.units")
    @Default("MINUTES")
    public String getRatesTimeUnit();

}

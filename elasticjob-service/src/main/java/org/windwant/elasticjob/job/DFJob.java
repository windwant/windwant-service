package org.windwant.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2017/5/25.
 */
public class DFJob implements DataflowJob {
    private static final Logger logger = LoggerFactory.getLogger(DFJob.class);

    public List fetchData(final ShardingContext shardingContext) {
        return new ArrayList(){{add(shardingContext.getJobName());}};
    }

    public void processData(ShardingContext shardingContext, List list) {
        logger.info("DFJob: " + list.toString());
    }
}

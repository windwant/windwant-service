package org.windwant.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by windwant on 2017/5/25.
 */
public class EJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(EJob.class);
    public void execute(ShardingContext shardingContext) {
        logger.info("EJOB: " + Math.random()*100);
    }
}

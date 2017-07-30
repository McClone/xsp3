package org.mcclone.common;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/30.
 */
public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {
        JobDetail jobDetail = new JobDetail("hello-job", HelloJob.class);
        Trigger trigger = new SimpleTrigger("hello-trigger", SimpleTrigger.REPEAT_INDEFINITELY, 2000);
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println(new Date());
        }
    }
}

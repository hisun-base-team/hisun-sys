/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.session.mgt.quartz;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class QuartzSessionValidationScheduler implements SessionValidationScheduler {


    static final String SESSION_MANAGER_KEY = "sessionManager";
    /*--------------------------------------------
    |             C O N S T A N T S             |
    ============================================*/
    /**
     * The default interval at which sessions will be validated (1 hour);
     * This can be overridden by calling {@link #setSessionValidationInterval(long)}
     */
    public static final long DEFAULT_SESSION_VALIDATION_INTERVAL = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

    /**
     * The name assigned to the quartz job.
     */
    private static final String JOB_NAME = "SessionValidationJob";

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationScheduler.class);

    /**
     * The configured Quartz scheduler to use to schedule the Quartz job.  If no scheduler is
     * configured, the schedular will be retrieved by calling {@link StdSchedulerFactory#getDefaultScheduler()}
     */
    private Scheduler scheduler;

    private boolean schedulerImplicitlyCreated = false;

    private boolean enabled = false;

    /**
     * The session manager used to validate sessions.
     */
    private ValidatingSessionManager sessionManager;

    /**
     * The session validation interval in milliseconds.
     */
    private long sessionValidationInterval = DEFAULT_SESSION_VALIDATION_INTERVAL;

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /**
     * Default constructor.
     */
    public QuartzSessionValidationScheduler() {
    }

    /**
     * Constructor that specifies the session manager that should be used for validating sessions.
     *
     * @param sessionManager the <tt>SessionManager</tt> that should be used to validate sessions.
     */
    public QuartzSessionValidationScheduler(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    protected Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            schedulerImplicitlyCreated = true;
        }
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Specifies how frequently (in milliseconds) this Scheduler will call the
     * {@link ValidatingSessionManager#validateSessions() ValidatingSessionManager#validateSessions()} method.
     *
     * <p>Unless this method is called, the default value is {@link #DEFAULT_SESSION_VALIDATION_INTERVAL}.
     *
     * @param sessionValidationInterval
     */
    public void setSessionValidationInterval(long sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Starts session validation by creating a Quartz simple trigger, linking it to
     * the {@link QuartzSessionValidationJob}, and scheduling it with the Quartz scheduler.
     */
    public void enableSessionValidation() {

        if (log.isDebugEnabled()) {
            log.debug("Scheduling session validation job using Quartz with " +
                    "session validation interval of [" + sessionValidationInterval + "]ms...");
        }

        try {
            /*SimpleTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .startNow()
                    .withIdentity(JOB_NAME, Scheduler.DEFAULT_GROUP)
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInMilliseconds(
                                            sessionValidationInterval)).build();

            JobDetail detail = JobBuilder
                    .newJob(QuartzSessionValidationJob.class)
                    .withIdentity(JOB_NAME, Scheduler.DEFAULT_GROUP).build();

            detail.getJobDataMap().put(SESSION_MANAGER_KEY, sessionManager);

            Scheduler scheduler = getScheduler();

            scheduler.scheduleJob(detail, trigger);*/
            if (schedulerImplicitlyCreated) {
                scheduler.start();
                if (log.isDebugEnabled()) {
                    log.debug("Successfully started implicitly created Quartz Scheduler instance.");
                }
            }
            this.enabled = true;

            if (log.isDebugEnabled()) {
                log.debug("Session validation job successfully scheduled with Quartz.");
            }

        } catch (SchedulerException e) {
            if (log.isErrorEnabled()) {
                log.error("Error starting the Quartz session validation job.  Session validation may not occur.", e);
            }
        }
    }

    public void disableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Stopping Quartz session validation job...");
        }

        Scheduler scheduler;
        try {
            scheduler = getScheduler();
            if (scheduler == null) {
                if (log.isWarnEnabled()) {
                    log.warn("getScheduler() method returned a null Quartz scheduler, which is unexpected.  Please " +
                            "check your configuration and/or implementation.  Returning quietly since there is no " +
                            "validation job to remove (scheduler does not exist).");
                }
                return;
            }
        } catch (SchedulerException e) {
            if (log.isWarnEnabled()) {
                log.warn("Unable to acquire Quartz Scheduler.  Ignoring and returning (already stopped?)", e);
            }
            return;
        }

        /*try {
            scheduler.unscheduleJob(new TriggerKey(JOB_NAME,Scheduler.DEFAULT_GROUP));
            if (log.isDebugEnabled()) {
                log.debug("Quartz session validation job stopped successfully.");
            }
        } catch (SchedulerException e) {
            if (log.isDebugEnabled()) {
                log.debug("Could not cleanly remove SessionValidationJob from Quartz scheduler.  " +
                        "Ignoring and stopping.", e);
            }
        }*/

        this.enabled = false;

        if (schedulerImplicitlyCreated) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                if (log.isWarnEnabled()) {
                    log.warn("Unable to cleanly shutdown implicitly created Quartz Scheduler instance.", e);
                }
            } finally {
                setScheduler(null);
                schedulerImplicitlyCreated = false;
            }
        }


    }
}

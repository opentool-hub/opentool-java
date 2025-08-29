package com.litevar.opentool.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServerStartupListener {

    private static final Logger logger = LoggerFactory.getLogger(ServerStartupListener.class);

    @Autowired
    private DaemonRegistrationService daemonRegistrationService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("ServerStartupListener: Application is ready, registering with daemon...");
        try {
            daemonRegistrationService.registerWithDaemon();
        } catch (Exception e) {
            logger.error("ServerStartupListener: Failed to register with daemon", e);
        }
    }
}
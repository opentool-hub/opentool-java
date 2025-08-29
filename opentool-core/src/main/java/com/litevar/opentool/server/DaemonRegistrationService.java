package com.litevar.opentool.server;

import com.litevar.opentool.util.JsonUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DaemonRegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(DaemonRegistrationService.class);

    private static final String DAEMON_REGISTER_URL = "http://localhost:19627/opentool-daemon/register";
    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 10;

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${opentool.host:http://localhost}")
    private String serverHost;

    private final OkHttpClient httpClient;

    public DaemonRegistrationService() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public void registerWithDaemon() {
        try {
            String jarPath = getJarPath();
            String hostUrl = buildHostUrl();
            long pid = getCurrentProcessId();

            Map<String, Object> registrationData = new HashMap<>();
            registrationData.put("file", jarPath);
            registrationData.put("host", hostUrl);
            registrationData.put("port", serverPort);
            registrationData.put("prefix", "/opentool");
            registrationData.put("pid", pid);

            String jsonBody = JsonUtil.toJsonString(registrationData);

            RequestBody body = RequestBody.create(
                    jsonBody,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(DAEMON_REGISTER_URL)
                    .post(body)
                    .build();

            logger.info("Registering with daemon at {} with data: {}", DAEMON_REGISTER_URL, jsonBody);

            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    logger.info("Successfully registered with daemon");
                } else {
                    logger.warn("Failed to register with daemon. Response code: {}, message: {}",
                            response.code(), response.message());
                }
            }

        } catch (Exception e) {
            logger.error("Error occurred while registering with daemon. This will not affect normal operation.", e);
        }
    }

    private String getJarPath() {
        try {
            String jarPath = DaemonRegistrationService.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
            return jarPath;
        } catch (Exception e) {
            logger.warn("Could not determine JAR path, using default", e);
            return System.getProperty("user.dir");
        }
    }

    private String buildHostUrl() {
        String host = serverHost;
        if (!host.startsWith("http://") && !host.startsWith("https://")) {
            host = "http://" + host;
        }

        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }

        return host + ":" + serverPort;
    }

    private long getCurrentProcessId() {
        try {
            return ProcessHandle.current().pid();
        } catch (Exception e) {
            logger.warn("Could not get process ID, using default", e);
            return -1;
        }
    }
}
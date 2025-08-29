package com.litevar.opentool.server;

import com.litevar.opentool.model.FunctionCall;
import com.litevar.opentool.model.OpenTool;
import com.litevar.opentool.model.ToolReturn;
import com.litevar.opentool.model.Version;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenToolService {
    private static final Logger logger = LoggerFactory.getLogger(OpenToolService.class);
    
    @Autowired
    private ApplicationContext applicationContext;

    private Tool toolInstance;

    @PostConstruct
    public void init() {
        logger.info("Initializing OpenToolService");
        loadToolFromApplicationContext();
    }

    private void loadToolFromApplicationContext() {
        Map<String, Tool> toolBeans = applicationContext.getBeansOfType(Tool.class);
        logger.debug("Found {} Tool implementations in application context", toolBeans.size());

        if (!toolBeans.isEmpty()) {
            if (toolBeans.size() > 1) {
                String allImpl = toolBeans.values().stream().map(tool -> tool.getClass().getSimpleName()).collect(Collectors.joining(","));
                logger.error("Multiple Tool implementations found: {}", allImpl);
                throw new IllegalStateException("Only one implementation of Tool interface is allowed. Found " + toolBeans.size() + " implementations: " + allImpl);
            }
            toolInstance = toolBeans.values().iterator().next();
            logger.info("Loaded Tool implementation: {}", toolInstance.getClass().getSimpleName());
        } else {
            logger.warn("No Tool implementation found in application context");
        }
    }

    public OpenTool createAggregatedOpenTool() {
        if (toolInstance == null) {
            logger.warn("No tool instance available for creating aggregated OpenTool");
            return null;
        }

        try {
            logger.debug("Creating aggregated OpenTool from tool instance: {}", toolInstance.getClass().getSimpleName());
            OpenTool openTool = toolInstance.load();
            logger.info("Successfully created aggregated OpenTool with {} functions", 
                    openTool != null && openTool.getFunctions() != null ? openTool.getFunctions().size() : 0);
            return openTool;
        } catch (Exception e) {
            logger.error("Failed to load tool description from {}", toolInstance.getClass().getSimpleName(), e);
            return null;
        }
    }

    public Version version() {
        logger.debug("Returning version information");
        Version version = new Version();
        version.setVersion("1.0.0");
        return version;
    }

    public OpenTool load() {
        logger.debug("Loading OpenTool configuration");
        return createAggregatedOpenTool();
    }

    public ToolReturn call(FunctionCall functionCall) {
        if (toolInstance == null) {
            logger.error("No tool implementation found for function call: {}", functionCall != null ? functionCall.getName() : "null");
            throw new RuntimeException("No tool implementation found");
        }

        logger.info("Calling function: {} with arguments: {}", functionCall.getName(), functionCall.getArguments());
        
        try {
            Map<String, Object> result = toolInstance.call(functionCall.getName(), functionCall.getArguments());
            logger.debug("Function {} executed successfully", functionCall.getName());
            
            ToolReturn toolReturn = new ToolReturn();
            toolReturn.setResult(result);
            return toolReturn;
        } catch (Exception e) {
            logger.error("Error executing function: {}", functionCall.getName(), e);
            throw e;
        }
    }

}
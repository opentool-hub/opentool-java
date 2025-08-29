package com.litevar.opentool.server;

import com.litevar.opentool.model.OpenTool;

import java.util.Map;

public interface Tool {

    /**
     * Call the specified method with the provided arguments
     *
     * @param name      The name of the method to call
     * @param arguments The arguments for the method call, can be null
     * @return The result of the method call
     */
    Map<String, Object> call(String name, Map<String, Object> arguments);

    /**
     * Load tool description information
     *
     * @return OpenTool description or null if not available
     */
    OpenTool load();
}
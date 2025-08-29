package com.litevar.opentool.client;

import com.litevar.opentool.model.FunctionCall;
import com.litevar.opentool.model.OpenTool;
import com.litevar.opentool.model.ToolReturn;
import com.litevar.opentool.model.Version;

public interface Client {

    /**
     * Get server version information
     *
     * @return Version object containing server version
     */
    Version version();

    /**
     * Make a tool call with the provided function call information
     *
     * @param functionCall The function call information
     * @return ToolReturn containing the result of the call
     */
    ToolReturn call(FunctionCall functionCall);

    /**
     * Load tool description information
     *
     * @return OpenTool description or null if not available
     */
    OpenTool load();
}
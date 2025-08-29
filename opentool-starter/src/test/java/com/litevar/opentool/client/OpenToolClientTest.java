package com.litevar.opentool.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.litevar.opentool.model.FunctionCall;
import com.litevar.opentool.model.OpenTool;
import com.litevar.opentool.model.ToolReturn;
import com.litevar.opentool.model.Version;
import com.litevar.opentool.starter.OpenToolStarterApplication;
import com.litevar.opentool.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * client example
 *
 * @author uncle
 * @since 2025/8/7 14:14
 */
@SpringBootTest(classes = OpenToolStarterApplication.class)
public class OpenToolClientTest {

    @Test
    public void test() throws JsonProcessingException {
        OpenToolClient client = new OpenToolClient("http://localhost:9627", "demo-api-key");

        //get version
        Version version = client.version();
        System.out.println("Server version: " + version.getVersion());

        //load tool
        OpenTool tool = client.load();
        System.out.println("Server tool: " + JsonUtil.toJsonString(tool));

        FunctionCall functionCall = new FunctionCall();
        functionCall.setName("get_current_weather");

        //call argument
        Map<String, String> currentWeatherInfo = new HashMap<>();
        currentWeatherInfo.put("format", "celsius");
        currentWeatherInfo.put("location", "San Francisco");
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("currentWeatherInfo", currentWeatherInfo);
        functionCall.setArguments(arguments);
        functionCall.setId("call-" + UUID.randomUUID());

        ToolReturn toolReturn = client.call(functionCall);

        System.out.println("tool result: " + toolReturn.getResult());

        client.close();
    }
}

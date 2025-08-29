package com.litevar.opentool.starter.tool;

import com.litevar.opentool.model.*;
import com.litevar.opentool.server.Tool;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author uncle
 * @since 2025/8/7 11:32
 */
@Component
public class ExampleTool implements Tool {
    @Override
    public Map<String, Object> call(String name, Map<String, Object> arguments) {
        if ("get_current_weather".equals(name)) {
            Map<String, Object> currentWeatherInfo = (Map<String, Object>) arguments.get("currentWeatherInfo");
            if (currentWeatherInfo != null) {
                String location = (String) currentWeatherInfo.get("location");
                String format = (String) currentWeatherInfo.get("format");

                String weather = generateMockWeather(location, format);
                return Map.of("currentWeather", weather);
            }
        }
        return Map.of();
    }

    private String generateMockWeather(String location, String format) {
        String temperature = "celsius".equals(format) ? "22°C" : "72°F";
        return String.format("Current weather in %s: Sunny, %s, humidity 65%%", location, temperature);
    }

    @Override
    public OpenTool load() {
        OpenTool openTool = new OpenTool();
        openTool.setOpentool("1.0.0");

        Info info = new Info();
        info.setTitle("Weather Example");
        info.setVersion("1.0.0");
        openTool.setInfo(info);

        Schema locationSchema = new Schema();
        locationSchema.setType("string");
        locationSchema.setDescription("The city and state, e.g. San Francisco, CA");

        Schema formatSchema = new Schema();
        formatSchema.setType("string");
        formatSchema.setDescription("The temperature unit to use. Infer this from the users location.");
        formatSchema.setEnumValues(Arrays.asList("celsius", "fahrenheit"));

        Schema parameterSchema = new Schema();
        parameterSchema.setType("object");
        parameterSchema.setProperties(Map.of(
                "location", locationSchema,
                "format", formatSchema
        ));
        parameterSchema.setRequired(Arrays.asList("location", "format"));

        Parameter parameter = new Parameter();
        parameter.setName("currentWeatherInfo");
        parameter.setSchema(parameterSchema);
        parameter.setRequired(true);

        Schema returnSchema = new Schema();
        returnSchema.setType("string");

        Return returnValue = new Return();
        returnValue.setName("currentWeather");
        returnValue.setSchema(returnSchema);

        Function function = new Function();
        function.setName("get_current_weather");
        function.setDescription("Get the current weather");
        function.setParameters(List.of(parameter));
        function.setReturnValue(returnValue);

        openTool.setFunctions(List.of(function));

        return openTool;
    }
}

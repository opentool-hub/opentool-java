package com.litevar.opentool.client;

import com.litevar.opentool.exception.*;
import com.litevar.opentool.model.*;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class OpenToolClient implements Client {

    private static final Logger logger = LoggerFactory.getLogger(OpenToolClient.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String baseUrl;
    private final OkHttpClient httpClient;

    public OpenToolClient(String baseUrl, String apiKey) {
        logger.info("Initializing OpenToolClient with baseUrl: {}", baseUrl);
        // 确保URL以/opentool结尾
        if (baseUrl.endsWith("/")) {
            this.baseUrl = baseUrl + "opentool";
        } else if (baseUrl.endsWith("/opentool")) {
            this.baseUrl = baseUrl;
        } else {
            this.baseUrl = baseUrl + "/opentool";
        }
        logger.debug("Final baseUrl: {}", this.baseUrl);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectionPool(new okhttp3.ConnectionPool(1, 5, TimeUnit.MINUTES));

        if (apiKey != null && !apiKey.trim().isEmpty()) {
            builder.addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + apiKey)
                        .build();
                return chain.proceed(newRequest);
            });
        }

        this.httpClient = builder.build();
        logger.info("OpenToolClient initialized successfully");
    }

    private Request.Builder createRequestBuilder(String url) {
        return new Request.Builder().url(url);
    }

    private <T> T executeRequest(Request request, Function<String, T> responseParser) {
        try (Response response = httpClient.newCall(request).execute()) {
            handleHttpErrors(response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new ResponseNullException(500, "Response body is null");
            }

            String responseString = responseBody.string();
            if (responseString.trim().isEmpty()) {
                return responseParser.apply("");
            }

            return responseParser.apply(responseString);
        } catch (IOException e) {
            logger.error("IO exception occurred while executing request to {}", request.url(), e);
            throw new OpenToolServerNoAccessException(404, "Please check OpenTool Server is RUNNING or NOT");
        } catch (RuntimeException e) {
            logger.error("Runtime exception occurred while executing request to {}", request.url(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while executing request to {}", request.url(), e);
            throw new OpenToolServerCallException("Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public Version version() {
        String url = baseUrl + "/version";
        logger.debug("Calling version endpoint: {}", url);
        Request request = createRequestBuilder(url).get().build();

        return executeRequest(request, responseString -> {
            if (responseString.trim().isEmpty()) {
                throw new ResponseNullException(500, "Response is null");
            }
            try {
                Version version = Version.fromJsonString(responseString);
                logger.info("Retrieved version: {}", version.getVersion());
                return version;
            } catch (Exception e) {
                logger.error("Failed to parse version response: {}", responseString, e);
                throw new OpenToolServerCallException("Failed to parse response: " + e.getMessage());
            }
        });
    }

    @Override
    public ToolReturn call(FunctionCall functionCall) {
        if (functionCall == null) {
            logger.error("FunctionCall object is null");
            throw new OpenToolServerCallException("FunctionCall object is null");
        }
        String url = baseUrl + "/call";
        logger.debug("Calling function: {} with arguments: {} at url: {}", 
                functionCall.getName(), functionCall.getArguments(), url);

        JsonRpcRequest jsonRpcRequest = new JsonRpcRequest(
                functionCall.getName(),
                functionCall.getArguments(),
                functionCall.getId()
        );

        try {
            String requestBody = jsonRpcRequest.toJsonString();
            logger.debug("Request body: {}", requestBody);
            RequestBody body = RequestBody.create(requestBody, JSON);
            Request request = createRequestBuilder(url).post(body).build();

            return executeRequest(request, responseString -> {
                if (responseString.trim().isEmpty()) {
                    throw new ResponseNullException(500, "Response is null");
                }

                try {
                    JsonRpcResponse jsonRpcResponse = JsonRpcResponse.fromJsonString(responseString);

                    if (jsonRpcResponse.getError() != null) {
                        JsonRpcError error = jsonRpcResponse.getError();
                        throw new OpenToolServerCallException(error.getMessage());
                    }

                    if (jsonRpcResponse.getResult() == null) {
                        throw new ErrorNullException(500, "Error is null");
                    }

                    ToolReturn toolReturn = new ToolReturn(jsonRpcResponse.getId(), jsonRpcResponse.getResult());
                    logger.info("Function {} executed successfully with id: {}", functionCall.getName(), toolReturn.getId());
                    return toolReturn;
                } catch (Exception e) {
                    logger.error("Failed to parse call response: {}", responseString, e);
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    }
                    throw new OpenToolServerCallException("Failed to parse response: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new OpenToolServerNoAccessException(404, "Please check OpenTool Server is RUNNING or NOT");
        }
    }

    @Override
    public OpenTool load() {
        String url = baseUrl + "/load";
        logger.debug("Loading tools from endpoint: {}", url);
        Request request = createRequestBuilder(url).get().build();

        return executeRequest(request, responseString -> {
            if (responseString.trim().isEmpty() || responseString.trim().equals("{}")) {
                return null;
            }
            try {
                OpenTool openTool = OpenTool.fromJsonString(responseString);
                logger.info("Loaded {} functions", openTool.getFunctions() != null ? openTool.getFunctions().size() : 0);
                return openTool;
            } catch (Exception e) {
                logger.error("Failed to parse load response: {}", responseString, e);
                throw new OpenToolServerCallException("Failed to parse response: " + e.getMessage());
            }
        });
    }

    private void handleHttpErrors(Response response) {
        if (response.code() == 401) {
            throw new OpenToolServerUnauthorizedException(401, "Please check API Key is VALID or NOT");
        } else if (response.code() == 404) {
            throw new OpenToolServerNoAccessException(404, "Please check OpenTool Server is RUNNING or NOT");
        } else if (!response.isSuccessful()) {
            throw new OpenToolServerCallException("HTTP error: " + response.code() + " " + response.message());
        }
    }

    public void close() {
        if (httpClient != null) {
            httpClient.dispatcher().executorService().shutdown();
            httpClient.connectionPool().evictAll();
        }
    }
}
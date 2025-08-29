package com.litevar.opentool.server;

import com.litevar.opentool.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opentool")
public class OpenToolController {

    private static final Logger logger = LoggerFactory.getLogger(OpenToolController.class);
    
    @Autowired
    private OpenToolService openToolService;

    @GetMapping("/version")
    public ResponseEntity<?> version() {
        logger.info("Received version request");
        Version version = openToolService.version();
        logger.debug("Returning version: {}", version.getVersion());
        return ResponseEntity.ok(version);
    }

    @GetMapping("/load")
    public ResponseEntity<?> load() {
        logger.info("Received load request");
        OpenTool openTool = openToolService.load();
        logger.debug("Returning OpenTool with {} functions", 
                openTool != null && openTool.getFunctions() != null ? openTool.getFunctions().size() : 0);
        return ResponseEntity.ok(openTool);
    }

    @PostMapping("/call")
    public ResponseEntity<?> call(@RequestBody JsonRpcRequest request) {
        logger.info("Received call request for method: {} with id: {}", request.getMethod(), request.getId());
        logger.debug("Request params: {}", request.getParams());
        
        try {
            FunctionCall functionCall = new FunctionCall(
                    request.getId(),
                    request.getMethod(),
                    request.getParams()
            );

            ToolReturn toolReturn = openToolService.call(functionCall);

            JsonRpcResponse response = new JsonRpcResponse();
            response.setId(request.getId());
            response.setResult(toolReturn.getResult());
            
            logger.info("Successfully processed call for method: {} with id: {}", request.getMethod(), request.getId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error processing call for method: {} with id: {}", request.getMethod(), request.getId(), e);
            
            JsonRpcResponse errorResponse = new JsonRpcResponse();
            errorResponse.setId(request.getId());

            JsonRpcError error = new JsonRpcError();
            error.setCode(500);
            error.setMessage("Internal error: " + e.getMessage());
            errorResponse.setError(error);

            return ResponseEntity.ok(errorResponse);
        }
    }

}
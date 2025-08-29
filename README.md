# OpenTool Java SDK

English | [中文](README_CN.md)

Java SDK for OpenTool client and server, including OpenTool JSON Parser

## System Requirements

- Java 17+
- Maven 3.6+
- Spring Boot 3.0+ (for server)

## Project Structure

```
opentool-java/
├── pom.xml                  # Parent project POM
├── opentool-core/           # Core library module
│   ├── src/main/java/       # Library source code
│   └── pom.xml             # Core module POM (provided scope)
└── opentool-starter/        # Standalone starter module
    ├── src/main/java/       # Starter application
    └── pom.xml             # Starter module POM
```

## Client Usage

API Endpoints:

- `GET /opentool/version` - Get server version
- `GET /opentool/load` - Load tool descriptions
- `POST /opentool/call` - Execute function calls (JSON-RPC 2.0)

Add the core dependency to your application:

```xml
<dependency>
    <groupId>com.litevar.opentool</groupId>
    <artifactId>opentool-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

Usage example:

```java
import com.litevar.opentool.client.OpenToolClient;
import com.litevar.opentool.model.*;
import java.util.*;

// Create client
OpenToolClient client = new OpenToolClient("http://localhost:9627", "your-api-key");

// Call function
Map<String, Object> weatherInfo = Map.of(
    "location", "Beijing, China",
    "format", "celsius"
);

Map<String, Object> arguments = Map.of("weatherInfo", weatherInfo);

FunctionCall functionCall = new FunctionCall(
    UUID.randomUUID().toString(),
    "get_current_weather",
    arguments
);

ToolReturn result = client.call(functionCall);
System.out.println("Result: " + result.getResult());

client.close();
```

## Server Usage

### Option 1: Use as Library Dependency

#### 1. Create Your Spring Boot Application

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyServer {
    public static void main(String[] args) {
        SpringApplication.run(MyServer.class, args);
    }
}
```

#### 2. Add Dependency

Add the core dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.litevar.opentool</groupId>
    <artifactId>opentool-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 3. Configuration

Configure via `application.yml`:

```yaml
opentool:
  enabled: true
  # API key配置，支持单个或多个key（用逗号分隔）,示例: key1,key2,key3
  api-key: demo-api-key
```

#### 4. Create Tool Implementation

Create a class with `@Component` annotation that implements the `Tool` interface:

```java
@Component
public class MyTool implements Tool {
    @Override
    public Map<String, Object> call(String name, Map<String, Object> arguments) {
        // Handle function calls
    }
    
    @Override
    public OpenTool load() {
        // Define tool metadata and function schemas
    }
}
```

For a complete implementation example, see the `ExampleTool` class: `opentool-starter/src/main/java/com/litevar/opentool/starter/tool/ExampleTool.java`.

**Important Constraints**:
- **Only ONE tool implementation class is allowed per application**. The system will throw an `IllegalStateException` if multiple `Tool` implementations are detected.
- Tool classes annotated with `@Component` will be automatically discovered by the Spring IoC container without any additional configuration.

### Option 2: Use Standalone Starter Module

The `opentool-starter` module provides a ready-to-use application:

```bash
# Build and run the standalone starter
cd opentool-starter
mvn spring-boot:run

# Or build the entire project and run the JAR
mvn clean package -DskipTests
cd opentool-starter
java -jar target/opentool-starter-1.0.0.jar
```

Add your tools to the `opentool-starter/src/main/java/com/litevar/opentool/starter/tool/` package and they will be automatically loaded.

## Project Packaging

For library release, only package the `opentool-core` module (the `opentool-starter` module does not need to be released):

```bash
# Only package the core module
cd opentool-core
mvn clean package

# Or package the core module from the root directory
mvn clean package -pl opentool-core
```

The packaged JAR file is located at `opentool-core/target/opentool-core-1.0.0.jar`.
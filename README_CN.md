# OpenTool Java SDK

[English](README.md) | 中文

OpenTool client和sever的Java SDK,并连带OpenTool JSON的Parser

## 系统要求

- Java 17+
- Maven 3.6+
- Spring Boot 3.0+ (服务端)

## 项目结构

```
opentool-java/
├── pom.xml                  # 父项目 POM
├── opentool-core/           # 核心库模块
│   ├── src/main/java/       # 库源代码
│   └── pom.xml             # 核心模块 POM（provided scope）
└── opentool-starter/        # 独立启动器模块
    ├── src/main/java/       # 启动应用程序
    └── pom.xml             # 启动器模块 POM
```

## 客户端使用

API 接口:

- `GET /opentool/version` - 获取服务器版本
- `GET /opentool/load` - 加载工具描述
- `POST /opentool/call` - 执行函数调用 (JSON-RPC 2.0)

添加核心依赖到您的应用程序：

```xml
<dependency>
    <groupId>com.litevar.opentool</groupId>
    <artifactId>opentool-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

调用方式:

```java
import com.litevar.opentool.client.OpenToolClient;
import com.litevar.opentool.model.*;
import java.util.*;

// 创建客户端
OpenToolClient client = new OpenToolClient("http://localhost:9627", "your-api-key");

// 调用函数
Map<String, Object> weatherInfo = Map.of(
    "location", "北京, 中国",
    "format", "celsius"
);

Map<String, Object> arguments = Map.of("weatherInfo", weatherInfo);

FunctionCall functionCall = new FunctionCall(
    UUID.randomUUID().toString(),
    "get_current_weather",
    arguments
);

ToolReturn result = client.call(functionCall);
System.out.println("结果: " + result.getResult());

client.close();
```

## 服务端使用

### 方式一：作为库依赖

#### 1. 创建您的 Spring Boot 应用程序

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

#### 2. 添加依赖

在您的 `pom.xml` 中添加核心依赖：

```xml
<dependency>
    <groupId>com.litevar.opentool</groupId>
    <artifactId>opentool-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 3. 配置

通过 `application.yml` 配置：

```yaml
opentool:
  enabled: true
  # API key配置，支持单个或多个key（用逗号分隔）,示例: key1,key2,key3
  api-key: demo-api-key
```

#### 4. 创建工具实现

创建一个使用 `@Component` 注解并实现 `Tool` 接口的类：

```java
@Component
public class MyTool implements Tool {
    @Override
    public Map<String, Object> call(String name, Map<String, Object> arguments) {
        // 处理函数调用
    }
    
    @Override
    public OpenTool load() {
        // 定义工具元数据和函数架构
    }
}
```

完整的实现示例请参考 `ExampleTool` 类：`opentool-starter/src/main/java/com/litevar/opentool/starter/tool/ExampleTool.java`。

**重要约束**：
- **每个应用程序只允许有一个工具实现类**。如果检测到多个 `Tool` 实现，系统将抛出 `IllegalStateException`。
- 使用`@Component`注解的工具类将被Spring IoC容器自动发现，无需任何额外配置。

### 方式二：使用独立启动器模块

`opentool-starter` 模块提供了一个即开即用的应用程序：

```bash
# 构建并运行独立启动器
cd opentool-starter
mvn spring-boot:run

# 或构建整个项目后运行 JAR
mvn clean package -DskipTests
cd opentool-starter
java -jar target/opentool-starter-1.0.0.jar
```

将您的工具添加到 `opentool-starter/src/main/java/com/litevar/opentool/starter/tool/` 包中，它们将被自动加载。

## 项目打包

对于库的发布，只需要打包 `opentool-core` 模块（`opentool-starter` 模块无需发布）：

```bash
# 只打包核心模块
cd opentool-core
mvn clean package

# 或从根目录打包核心模块
mvn clean package -pl opentool-core
```

打包后的 JAR 文件位于 `opentool-core/target/opentool-core-1.0.0.jar`。
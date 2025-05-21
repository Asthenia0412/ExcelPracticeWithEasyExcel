# Excel 文件传输接口

本项目基于 Spring Boot 和 [EasyExcel](https://github.com/alibaba/easyexcel) 库（版本 3.1.5）实现了一个 REST API，支持从服务端导出 Excel 文件以及上传 Excel 文件并解析为 JSON 数据。

## 目录
- [功能特性](#功能特性)
- [前置条件](#前置条件)
- [安装](#安装)
- [接口说明](#接口说明)
  - [导出 Excel 接口](#导出-excel-接口)
  - [导入 Excel 接口](#导入-excel-接口)
- [测试流程](#测试流程)
- [注意事项](#注意事项)
- [代码结构](#代码结构)
- [依赖](#依赖)

## 功能特性
- **导出 Excel**：通过 GET 请求下载包含用户数据的 `.xlsx` 文件。
- **导入 Excel**：通过 POST 请求上传 Excel 文件，解析后返回 JSON 格式数据。
- 支持 UTF-8 编码，正确处理中文等特殊字符。
- 使用 EasyExcel 实现高效的 Excel 文件读写。

## 前置条件
- Java 8 或以上版本
- Maven 3.6+
- Spring Boot 2.x 或 3.x
- 服务端运行环境（推荐本地测试端口：8080）

## 安装
1. 克隆项目仓库：
   ```bash
   git clone <repository-url>

2. 进入项目目录：
   ```bash
   cd <project-directory>
   ```
3. 使用 Maven 安装依赖并运行：
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. 服务启动后，访问 `http://localhost:8080`。

## 接口说明

### 导出 Excel 接口
- **请求方式**：GET
- **URL**：`http://localhost:8080/excel/export`
- **响应**：返回 Excel 文件流，客户端保存为 `.xlsx` 文件。
- **cURL 示例**：
  ```bash
  curl -X GET http://localhost:8080/excel/export --output test.xlsx
  ```
- **参数说明**：
  | 参数       | 说明               |
  | ---------- | ------------------ |
  | `--output` | 指定保存的文件路径 |

### 导入 Excel 接口
- **请求方式**：POST
- **URL**：`http://localhost:8080/excel/import`
- **请求格式**：`multipart/form-data`
- **参数**：
  | 字段名 | 类型       | 必填 | 说明              |
  | ------ | ---------- | ---- | ----------------- |
  | `file` | 二进制文件 | 是   | 上传的 Excel 文件 |
- **响应**：JSON 格式的解析结果（`List<User>`）。
- **cURL 示例**：
  ```bash
  curl -X POST http://localhost:8080/excel/import -F "file=@test.xlsx"
  ```
- **返回示例**：
  ```json
  [
    {"id": 1, "name": "张三", "age": 25, "email": "zhangsan@example.com"},
    {"id": 2, "name": "李四", "age": 30, "email": "lisi@example.com"}
  ]
  ```

## 测试流程
1. **下载 Excel 文件**：
   ```bash
   curl -X GET http://localhost:8080/excel/export -o test.xlsx
   ```
   - 检查当前目录是否生成 `test.xlsx` 文件。

2. **上传 Excel 文件**：
   ```bash
   curl -X POST http://localhost:8080/excel/import -F "file=@test.xlsx"
   ```
   - 验证返回的 JSON 数据是否符合预期。

3. **错误排查**：
   - 使用 `-v` 参数查看详细请求/响应日志：
     ```bash
     curl -v -X POST http://localhost:8080/excel/import -F "file=@test.xlsx"
     ```

## 注意事项
1. **文件路径**：
   - 确保 `test.xlsx` 存在于当前目录，或使用绝对路径（如 `@/tmp/test.xlsx`）。
2. **服务端配置**：
   - 导出接口需设置正确的响应头：`Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`。
   - 导入接口需支持 `multipart/form-data` 解析。
3. **中文编码**：
   - 文件名和内容均使用 UTF-8 编码，避免乱码。

## 代码结构
```
com.xiaoyongcai.io.edvancemyself
├── Controller
│   └── ExcelController.java    # REST 控制器，处理导出/导入请求
├── Service
│   └── ExcelService.java       # 业务逻辑，Excel 文件读写
├── Pojo
│   └── User.java               # 数据模型，映射 Excel 列
```

## 依赖
- **EasyExcel**：用于 Excel 文件读写
  ```xml
  <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>easyexcel</artifactId>
      <version>3.1.5</version>
  </dependency>
  ```
- Spring Boot Web
- Spring Boot Starter
```

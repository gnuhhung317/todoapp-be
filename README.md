# Todo List Application

Ứng dụng quản lý danh sách công việc (Todo List) được xây dựng bằng Spring Boot với PostgreSQL.

## Công nghệ sử dụng

- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## Cấu hình cơ sở dữ liệu

Ứng dụng được cấu hình để kết nối với PostgreSQL thông qua Neon.tech với chuỗi kết nối:
```
postgresql://vdt_owner:npg_gbZyN3MUexw6@ep-crimson-rain-a12fd1sf-pooler.ap-southeast-1.aws.neon.tech/vdt?sslmode=require
```

## Chạy ứng dụng

1. Đảm bảo bạn có Java 21 và Maven đã được cài đặt
2. Clone repository này
3. Chạy lệnh sau để khởi động ứng dụng:

```bash
./mvnw spring-boot:run
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## API Endpoints

### 1. Lấy danh sách tất cả công việc
```
GET /api/todos
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "Learn Spring Boot",
    "description": "Study Spring Boot framework and create a simple REST API",
    "completed": false,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
]
```

### 2. Lấy công việc theo ID
```
GET /api/todos/{id}
```

### 3. Tạo công việc mới
```
POST /api/todos
```

**Request Body:**
```json
{
  "title": "New Task",
  "description": "Task description",
  "completed": false
}
```

**Response:**
```json
{
  "id": 4,
  "title": "New Task",
  "description": "Task description",
  "completed": false,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 4. Cập nhật công việc
```
PUT /api/todos/{id}
```

**Request Body:**
```json
{
  "title": "Updated Task",
  "description": "Updated description",
  "completed": true
}
```

### 5. Xóa công việc
```
DELETE /api/todos/{id}
```

## Cấu trúc dự án

```
src/main/java/vdt/example/demo/
├── controller/
│   └── TodoController.java
├── service/
│   ├── TodoService.java
│   └── impl/
│       └── TodoServiceImpl.java
├── repository/
│   └── TodoRepository.java
├── model/
│   └── Todo.java
├── dto/
│   ├── TodoRequest.java
│   └── TodoResponse.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
└── config/
    └── DataInitializer.java
```

## Tính năng

- ✅ CRUD operations cho Todo
- ✅ Validation cho input data
- ✅ Global exception handling
- ✅ CORS support
- ✅ Automatic timestamp management
- ✅ Sample data initialization
- ✅ RESTful API design

## Testing API

Bạn có thể sử dụng Postman, cURL hoặc bất kỳ REST client nào để test API:

### Ví dụ với cURL:

```bash
# Lấy tất cả todos
curl -X GET http://localhost:8080/api/todos

# Tạo todo mới
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Task","description":"Test Description","completed":false}'

# Cập nhật todo
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Task","description":"Updated Description","completed":true}'

# Xóa todo
curl -X DELETE http://localhost:8080/api/todos/1
``` 
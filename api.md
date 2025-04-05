# 购物商城API文档

## 基础信息

- 基础URL: `http://api.example.com/v1`
- 所有请求都应在header中包含 `Content-Type: application/json`
- 认证接口除外，其他所有接口都需要在header中携带token: `Authorization: Bearer <token>`

## 状态码说明

| 状态码 | 说明 |
|--------|------|
| 200 | 请求成功 |
| 400 | 请求参数错误 |
| 401 | 未认证或认证失败 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 用户认证

### 用户登录

- **URL**: `/auth/login`
- **方法**: POST
- **请求参数**:

```json
{
  "username": "string",
  "password": "string"
}
```

- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "test_user",
      "email": "test@example.com"
    }
  },
  "message": "登录成功"
}
```

### 用户注册

- **URL**: `/auth/register`
- **方法**: POST
- **请求参数**:

```json
{
  "username": "string",
  "password": "string",
  "email": "string"
}
```

- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "username": "test_user",
    "email": "test@example.com"
  },
  "message": "注册成功"
}
```

## 商品管理

### 获取商品列表

- **URL**: `/products`
- **方法**: GET
- **查询参数**:
  - `page`: 页码（默认1）
  - `limit`: 每页数量（默认10）
  - `category`: 商品分类（可选）
  - `keyword`: 搜索关键词（可选）

- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "total": 100,
    "items": [{
      "id": 1,
      "name": "商品名称",
      "price": 99.99,
      "description": "商品描述",
      "image": "http://example.com/image.jpg",
      "category": "电子产品",
      "stock": 100
    }]
  },
  "message": "获取成功"
}
```

### 获取商品详情

- **URL**: `/products/:id`
- **方法**: GET
- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "name": "商品名称",
    "price": 99.99,
    "description": "商品详细描述",
    "images": [
      "http://example.com/image1.jpg",
      "http://example.com/image2.jpg"
    ],
    "category": "电子产品",
    "stock": 100,
    "specifications": {
      "color": "黑色",
      "size": "M"
    }
  },
  "message": "获取成功"
}
```

## 购物车管理

### 添加商品到购物车

- **URL**: `/cart/items`
- **方法**: POST
- **请求参数**:

```json
{
  "productId": 1,
  "quantity": 2
}
```

- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "productId": 1,
    "quantity": 2,
    "product": {
      "name": "商品名称",
      "price": 99.99
    }
  },
  "message": "添加成功"
}
```

### 获取购物车列表

- **URL**: `/cart/items`
- **方法**: GET
- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "items": [{
      "id": 1,
      "productId": 1,
      "quantity": 2,
      "product": {
        "name": "商品名称",
        "price": 99.99,
        "image": "http://example.com/image.jpg"
      }
    }],
    "totalPrice": 199.98
  },
  "message": "获取成功"
}
```

### 更新购物车商品数量

- **URL**: `/cart/items/:id`
- **方法**: PUT
- **请求参数**:

```json
{
  "quantity": 3
}
```

- **响应示例**:

```json
{
  "code": 200,
  "data": {
    "id": 1,
    "quantity": 3,
    "totalPrice": 299.97
  },
  "message": "更新成功"
}
```

### 删除购物车商品

- **URL**: `/cart/items/:id`
- **方法**: DELETE
- **响应示例**:

```json
{
  "code": 200,
  "message": "删除成功"
}
```

## 错误响应

当发生错误时，响应格式如下：

```json
{
  "code": 400,
  "message": "错误信息描述",
  "errors": [
    {
      "field": "username",
      "message": "用户名不能为空"
    }
  ]
}
```

## 注意事项

1. 所有涉及金额的字段均为数字类型，单位为元
2. 时间格式统一使用ISO 8601标准：YYYY-MM-DDTHH:mm:ss.sssZ
3. 分页接口均支持page和limit参数
4. 图片URL为完整的HTTP(S)地址
## 🧠 Sistema de E-commerce

### 📘 Contexto
**API RESTful em Java com Spring Boot** para gerenciar um sistema de **e-commerce**.  

---

## 🎯 Requisitos Técnicos

### 🧱 1. Modelagem de Domínio

#### `User`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `username` | String (50) | **Obrigatório** |
| `email` | String (120) | **Obrigatório** |
| `passwordHash` | String (255) | **Obrigatório** |
| `firstName` | String (50) | Opcional | 
| `lastName` | String (50) | Opcional |
| `role` | String(50) | ADMIN / USER |

#### `Address`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `street` | String (255) | **Obrigatório** |
| `number` | Integer | Opcional |
| `city` | String (255) | **Obrigatório** |
| `state` | String(255) | **Obrigatório** |
| `complement` | String (50) | Opcional | 
| `postalCode` | String (50) | Opcional |
| `user` | User | **Obrigatório**|

#### `Category`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `name` | String (100) | **Obrigatório** |

#### `Product`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `name` | String (100) | **Obrigatório** |
| `description` | String | Opcional |
| `price` | Decimal | **Obrigatório** |
| `stockQuantity` | Integer | **Obrigatório** |
| `category` | Category | **Obrigatório** |

#### `Orders`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `userId` | Integer | Id do `User` |
| `totalAmount` | Decimal | **Obrigatório** |
| `status` | String (50) | PENDING / CANCELED / DELIVERED / SHIPPED |
| `address` | Address | **Obrigatório** |

#### `OrderItems`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `order` | Orders | Identificador |
| `product` | Product | Identificador |
| `quantity` | Integer | **Obrigatório** |
| `price` | Decimal | **Obrigatório** |
| `subTotal` | Decimal | **Obrigatório** |

#### `Payment`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | Long | Identificador |
| `order` | Orders | Identificador |
| `paymentMethod` | String(50) | **Obrigatório** |
| `totalAmount` | Decimal | **Obrigatório** |
| `status` | String (50) | PENDING / CANCELED / PAID / REVERSED |

#### `Auditable`
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `createBy` | User | **Obrigatório** |
| `createdDate` | DATE | **Obrigatório** |
| `lastModifiedBy` | User | **Obrigatório** |
| `lastModifiedDate` | DATE | **Obrigatório** |

---

### 🌐 2. Endpoints REST

#### `Users`
| Método | Endpoint | ADMIN | USER |
|---------|-----------|-----------|-----------|
| **GET** |`/api/users`|✅|❌|
| **GET** |`/api/users/current`|✅|✅|
| **GET** |`/api/users/{id}`|✅|✅*)|
| **POST** |`/api/signin`|✅|✅|
| **POST** |`/api/signup`|✅|✅|
| **PUT** |`/api/users/{id}`|✅|✅*|

#### `Address`
| Método | Endpoint | ADMIN | USER |
|---------|-----------|-----------|-----------|
| **GET** |`/api/address`|❌|✅|
| **GET** |`/api/address/{id}`|❌|✅*|
| **POST** |`/api/address`|❌|✅|
| **PUT** |`/api/address/{id}`|❌|✅*|
| **DELETE** |`/api/address/{id}`|❌|✅*|

#### `Category`
| Método | Endpoint | ADMIN | USER |
|---------|-----------|-----------|-----------|
| **GET** |`/api/categories`|✅|❌|
| **GET** |`/api/categories/{id}`|✅|❌|
| **POST** |`/api/categories`|✅|❌|
| **PUT** |`/api/categories/{id}`|✅|❌|
| **DELETE** |`/api/categories/{id}`|✅|❌|

#### `Product`
| Método | Endpoint | ADMIN | USER |
|---------|-----------|-----------|-----------|
| **GET** |`/api/products`|✅|✅|
| **GET** |`/api/products/{id}`|✅|✅|
| **POST** |`/api/products`|✅|❌|
| **PUT** |`/api/products/{id}`|✅|❌|
| **DELETE** |`/api/products/{id}`|✅|❌|

#### `Order`
| Método | Endpoint | ADMIN | USER |
|---------|-----------|-----------|-----------|
| **GET** |`/api/orders`|✅|✅*|
| **GET** |`/api/orders/{id}`|✅|✅*|
| **POST** |`/api/orders`|❌|✅|
| **PUT** |`/api/orders/{id}/status`|✅|✅*|
---

## ✅ Tecnologias
- 🧑‍💻 **Java 21+** e **Spring Boot 3+**  
- 🧠 **Spring Data JPA**  
- 🗄️ Banco Relacional (**PostgreSQL**)
- 🗄️ Flyway Migrations
- ✔️ **Bean Validation**  
- ⚠️ Tratamento de erros com `@ControllerAdvice`  
- 📦 Uso de **DTOs** (`record` ou classes simples)  
- 📘 **README** explicando como rodar o projeto

---

### 🧾 Licença
Este projeto foi desenvolvido exclusivamente para fins de **estudo** e não deve ser utilizado para fins comerciais.

---

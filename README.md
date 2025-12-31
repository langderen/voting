# 🗳️ 软工之星投票系统 (Voting System Full-Stack)

这是一个基于 **Vue 3 + TypeScript** 前端与 **Spring Boot 3** 后端的全栈投票管理系统。项目专为“软件工程之星”评选场景设计，实现了用户认证、投票管理、数据可视化及评论互动等核心功能。

## 📚 目录

* [项目概述](#section1)
* [技术栈](#section2)
* [功能特性](#section3)
* [项目结构](#section4)
* [快速开始](#section5)
* [1. 数据库准备](#section6)
* [2. 后端启动](#section7)
* [3. 前端启动](#section8)


* [配置说明](#section9)

---

## 🚀 项目概述<a id="section1"></a>

本项目采用前后端分离架构。前端通过 Axios 与后端 RESTful API 进行交互，后端采用 Sa-Token 进行权限认证，并使用 MyBatis-Plus 操作 MySQL 数据库。系统支持普通用户参与投票、评论，以及管理员对投票项目、选项和用户的管理。

---

## 🛠 技术栈<a id="section2"></a>

### 🎨 前端 (View)

| 技术 | 说明 |
| --- | --- |
| **Framework** | Vue 3.5 (Composition API + Setup) |
| **Build Tool** | Vite 7 |
| **Language** | TypeScript |
| **UI Library** | Element Plus + TailwindCSS |
| **State Mgmt** | Pinia (含持久化插件) |
| **Visualization** | ECharts 6 (数据图表展示) |
| **Router** | Vue Router 4 |
| **HTTP** | Axios |

### ☕ 后端 (Contriller/voting_back)

| 技术 | 说明 |
| --- | --- |
| **Framework** | Spring Boot 3.5.x |
| **ORM** | MyBatis-Plus 3.5.14 |
| **Database** | MySQL 8.0 |
| **Auth** | Sa-Token 1.44 (轻量级权限认证) |
| **Utils** | Hutool 5.8, Lombok |

---

## ✨ 功能特性<a id="section3"></a>

### 👤 用户模块

* **注册/登录**：支持图形验证码校验 (`vue3-puzzle-vcode`)，密码加密存储。
* **权限管理**：基于 Sa-Token 的用户/管理员角色区分。
* **个人中心**：用户头像上传、基本信息修改。

### 🗳️ 投票核心

* **投票展示**：无限滚动加载投票列表，支持搜索与筛选。
* **投票逻辑**：
* 支持单选/多选。
* 匿名/实名投票配置。
* 投票时间范围限制 (Start/End Time)。
* 防重复投票机制。


* **结果分析**：使用 ECharts 展示投票结果统计图表。

### 💬 互动模块

* **评论系统**：支持对投票项目进行评论（嵌套/楼层结构）。
* **图片上传**：支持投票选项及用户头像的图片上传 (`ImageUploadController`)。
* **标签分类**：支持多标签关联投票项目。

---

## 📂 项目结构<a id="section4"></a>

```text
root/
├── models/                # 数据库脚本
│   └── vote.sql           # 初始化 SQL 文件
├── Contriller/            # 后端代码根目录
│   └── voting_back/       # Spring Boot 项目
│       ├── src/main/java/com/example/voting_back/
│       │   ├── controller/  # 控制层 (API 接口)
│       │   ├── entity/      # 实体类 (对应 DB 表)
│       │   ├── mapper/      # DAO 层 (MyBatis-Plus)
│       │   ├── service/     # 业务逻辑层
│       │   └── config/      # 配置类 (Sa-Token, Cors, etc.)
│       └── src/main/resources/
│           ├── application.yml      # 主配置
│           └── application-dev.yml  # 开发环境配置 (数据库连接等)
└── View/                  # 前端代码根目录
    ├── src/
    │   ├── api/           # Axios 请求封装
    │   ├── assets/        # 静态资源 (CSS, Images)
    │   ├── components/    # 公共组件
    │   ├── pages/         # 页面视图 (Home, Vote, Login...)
    │   ├── router/        # 路由配置
    │   └── stores/        # Pinia 状态管理
    └── vite.config.ts     # Vite 配置

```

---

## ⚡ 快速开始<a id="section5"></a>

### 1. 数据库准备<a id="section6"></a>

1. 确保本地已安装 **MySQL 8.0+**。
2. 创建一个新的数据库（推荐命名为 `voting_system`）。
3. 执行 `models/vote.sql` 脚本，初始化表结构（`users`, `polls`, `options`, `votes`, `comments` 等）。

```sql
-- 登录 MySQL 后执行
source /path/to/models/vote.sql;

```

### 2. 后端启动<a id="section7"></a>

1. 进入后端目录：`cd Contriller/voting_back`
2. 修改配置文件：打开 `src/main/resources/application-dev.yml`，配置你的数据库账号密码。
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/voting_system?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    username: root  # 修改为你的用户名
    password: 123456 # 修改为你的密码

```


3. 运行项目：
```bash
# 使用 Maven 运行
mvn spring-boot:run

```


后端默认运行在 `8080` 端口（或根据 yml 配置）。

### 3. 前端启动<a id="section8"></a>

1. 进入前端目录：`cd View`
2. 安装依赖：
```bash
npm install

```


3. 启动开发服务器：
```bash
npm run dev

```


4. 访问浏览器：通常为 `http://localhost:5173`。

---

## ⚙️ 配置说明

### 跨域配置 (CORS)

后端已在 `config/CustomCorsFilter.java` 中配置了跨域策略，默认允许前端访问。如果前后端端口不一致，请确保 allowed-origins 包含前端地址。

### 图片上传

`ImageUploadController` 处理图片上传，请检查 `application.yml` 中关于文件存储路径的配置（如腾讯云 COS 或本地路径），确保上传目录有写入权限。

### 权限认证

项目使用 **Sa-Token**。

* 前端请求头需携带 Token（名称通常为 `satoken` 或配置文件中指定的名称）。
* 登录成功后，后端会返回 Token，前端需将其存储在 Cookie 或 LocalStorage 中。

---

## 🤝 贡献与反馈<a id="section9"></a>

如果你发现任何 Bug 或有新功能建议，请提交 Issue 或 Pull Request。

---

*Created by Langderen | 2024*

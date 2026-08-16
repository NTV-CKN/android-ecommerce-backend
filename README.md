# PKCN — Android E-commerce Backend

Backend hệ thống thương mại điện tử phục vụ ứng dụng Android, được xây dựng trên nền tảng **Spring Boot 4.x** và **Java 21**. Hệ thống cung cấp các chuẩn RESTful API, tích hợp Spring Security, JWT và Spring AI.

---

## 🛠 Công Nghệ (Tech Stack)

| Thành phần | Công nghệ sử dụng |
| --- | --- |
| **Language** | Java 21 |
| **Framework** | Spring Boot 4.x |
| **ORM / Database** | Spring Data JPA, Hibernate, MySQL 8.x |
| **Architecture** | Layered MVC (Controller $\rightarrow$ Service $\rightarrow$ Repository / EntityManager) |
| **Security & Auth** | Spring Security, JJWT (JSON Web Token) |
| **Integration** | Spring Mail (Gmail SMTP), Spring AI (Google GenAI / Gemini) |
| **Build Tool** | Maven (kèm `./mvnw` wrapper) |

---

## 📋 Yêu Cầu Cấu Hình Hệ Thống

* **JDK**: 21 trở lên
* **Build Tool**: Maven 3.x (hoặc dùng Wrapper sẵn trong repo)
* **Database Server**: MySQL 8.x (khuyên dùng WampServer $\ge$ 3.4.0)
* **Database Client**: Navicat for MySQL ($\ge$ 16), MySQL Workbench hoặc CLI
* **Version Control**: Git

---

## 📂 File Cấu Trúc SQL Schema

Cấu trúc Database được lưu trữ trực tiếp trong dự án tại:

* Đường dẫn tương đối: `src/main/resources/schema.sql`
* [Tải trực tiếp Raw File schema.sql từ GitHub](https://raw.githubusercontent.com/NTV-CKN/android-ecommerce-backend/master/src/main/resources/schema.sql)

---

## 🚀 Hướng Dẫn Cài Đặt Chi Tiết

### 1. Clone Source Code

```bash
git clone https://github.com/NTV-CKN/android-ecommerce-backend.git
cd android-ecommerce-backend
git checkout master

```

### 2. Khởi Động Database Server

* Khởi chạy **WampServer** (đảm bảo Apache và MySQL đã chuyển sang màu xanh / Running).
* Mở công cụ quản lý MySQL (Navicat, Workbench hoặc phpMyAdmin).

### 3. Tạo Database `pkcn_app`

Thực thi lệnh SQL sau trong Client để tạo CSDL:

```sql
CREATE DATABASE IF NOT EXISTS pkcn_app 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

```

### 4. Import Schema

Bạn chọn **1 trong 2 cách** sau để import cấu trúc bảng:

* **Cách A: Dùng Navicat (GUI)**
1. Mở Navicat $\rightarrow$ Kết nối MySQL (`localhost:3306`).
2. Chuột phải vào database `pkcn_app` $\rightarrow$ Chọn **Execute SQL File...**
3. Trỏ tới file: `src/main/resources/schema.sql`.
4. Bấm **Run** *(File đã tích hợp `SET FOREIGN_KEY_CHECKS = 0` để tránh lỗi khóa ngoại)*.


* **Cách B: Dùng MySQL CLI**
```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS pkcn_app DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p pkcn_app < src/main/resources/schema.sql

```



### 5. Cấu Hình Environment & `application.properties`

> **⚠️ Lưu ý an toàn:** Không commit thông tin nhạy cảm (Password, JWT Secret, API Key) lên GitHub. Hãy tạo file local `src/main/resources/application-local.properties` hoặc thiết lập qua Biến môi trường (Environment Variables).

Mẫu cấu hình tham khảo (`src/main/resources/application.properties`):

```properties
# Server configuration
spring.application.name=pkcn
server.port=8080
server.address=0.0.0.0

# JWT Configuration
app.jwt.secretkey=${APP_JWT_SECRET:YOUR_VERY_LONG_SECRET_KEY_HERE}
app.jwt.expire.access.token=1200000
app.jwt.expire.refresh.token=604800000

# Mail Configuration (Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${SPRING_MAIL_USERNAME}
spring.mail.password=${SPRING_MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pkcn_app?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME:jjj}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Google GenAI / Gemini Configuration
spring.ai.google.genai.api-key=${GOOGLE_GENAI_API_KEY}
spring.ai.google.genai.chat.options.model=gemini-3.1-flash-lite
spring.ai.google.genai.chat.options.temperature=0.5

```

---

## 🛠 Xử Lý Lỗi Thường Gặp (Troubleshooting)

* **Lỗi kết nối CSDL (Connection Refused):** Đảm bảo MySQL service đang chạy tại port `3306`, kiểm tra lại `username`/`password` trong `application.properties`.
* **Lỗi Java Version:** Xác nhận phiên bản Java cài trên máy bằng `java -version` (bắt buộc JDK 21+).
* **Lỗi Xung Đột Port 8080:** Thay đổi `server.port` trong file cấu hình sang port khác (ví dụ: `8081`).
* **Lỗi Gửi Email (SMTP Authentication):** Đảm bảo bạn đã dùng **App Password** của Google thay vì mật khẩu email gốc và đã bật tính năng 2FA.

---

## 📌 Ghi Chú Kỹ Thuật (Architecture Notes)

* Hệ thống áp dụng mô hình layered architecture chuẩn: **Controller $\rightarrow$ Service $\rightarrow$ Repository**.
* Tùy biến truy vấn phức tạp (Dynamic Query, Multi-Fetch) được tối ưu ở tầng Service thông qua `EntityManager` và JPQL để tránh hiện tượng N+1 Query.

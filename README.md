
# Project Java Spring Boot - Quản lý Danh Dạ - Phùng Huy Quang

## Môi trường phát triển
 - Java 8
 - Java Spring Boot
 - Spring Data Jpa
 - Spring Security
 - Thymeleaf
 - DB MYSQL 8.25
 - Tools: Lombok, Devtools, 
## Nội dung
  - Quản lý thông tin liên lạc và danh bạ cá nhân.
### Login
    Nhập thông tin user/password để thực hiện đăng nhập
    Password mã hóa Bcry
    
    ![2](https://user-images.githubusercontent.com/85112203/132131818-3ab43e6c-1079-434f-99d5-aea19760ab74.JPG)

### Đăng ký user
    Địa chỉ email phải hợp lệ
    Validate các trường email là duy nhất
### Lấy lại mật khẩu
    Quên mật khẩu
    Gửi mail mã OTP để xác nhận lấy lại mật khẩu
### Thay đổi thông tin cá nhân
    Được thay đổi thông tin cá nhân và thay đổi password
### Các chức năng của user
#### Thêm contact
    User có thể thêm liên lạc mới với các thuộc tính:
      + Ảnh
      + Tên
      + Nickname
      + Email
      + Số điện thoại
      + Công việc
      + Mô tả thêm
    Id User sẽ được lưu kèm để xác minh
#### Sửa contact
    User có thể thay đổi các thuộc tính ngoại trừ Id và email không được trùng
#### Xóa contact
    User có thể xóa contact của mình bất cứ lúc nào.

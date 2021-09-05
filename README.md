
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
    - Nhập thông tin user/password để thực hiện đăng nhập
    - Password mã hóa Bcry
![login](https://user-images.githubusercontent.com/85112203/132133325-c36ef237-448f-438d-bde7-13fd304481a2.JPG)

### Đăng ký user
    - Địa chỉ email phải hợp lệ
    - Validate các trường email là duy nhất
    - Phải tích chọn chấp nhận điều khoản, nếu không sẽ không thể đăng ký
![register](https://user-images.githubusercontent.com/85112203/132133356-3947a4fd-2c79-4a30-ae4d-957bfe3e813b.JPG)

### Lấy lại mật khẩu
    - Quên mật khẩu
![quenpass](https://user-images.githubusercontent.com/85112203/132133360-417c14a9-52bc-4b6c-b7b2-a7ef5dc1ce50.JPG)
    
    - Hệ thông sẽ tự động gửi mail mã OTP gồm 4 số để xác nhận lấy lại mật khẩu
![otp](https://user-images.githubusercontent.com/85112203/132133385-4a1bfa0a-1a5e-430c-97f9-255eeb148920.JPG)
    
    - Sau khi xác nhận OTP, User có thể đăng ký password mới
![newpass](https://user-images.githubusercontent.com/85112203/132133411-3fabaee4-3525-490f-933a-0e9c95a78fbe.JPG)

### Các chức năng của user
#### Thêm contact
    - User có thể thêm liên lạc mới với các thuộc tính:
      + Ảnh
      + Tên
      + Nickname
      + Email
      + Số điện thoại
      + Công việc
      + Mô tả thêm
    - Id User sẽ được lưu kèm để xác minh
    - tích hợp TinyMCE trong phần mô tả

![add](https://user-images.githubusercontent.com/85112203/132133449-e24c6deb-a0e0-42aa-828d-31f8b94a69fa.JPG)

#### Sửa contact
    - User có thể thay đổi các thuộc tính ngoại trừ Id và email không được trùng
    
![update](https://user-images.githubusercontent.com/85112203/132133525-f1c81821-bd95-4df7-9673-0b59d6fdebdc.JPG)

#### Xóa contact
    - User có thể xóa contact của mình bất cứ lúc nào.
    - 1 Bảng thông báo sẽ hiện lên hỏi User xác nhận xóa
    
![delêt](https://user-images.githubusercontent.com/85112203/132133590-714f5542-1b41-47cb-b52c-eafa4784a70a.JPG)

### Thanh menu
#### Home
    - Trang home sẽ hiển thị btn New Contacts để User thêm contact
![home](https://user-images.githubusercontent.com/85112203/132133601-295f9872-3c2e-493c-9985-457e5cfeca5d.JPG)

#### Contact list
    - Hiển thị danh sách các contact đã được thêm
    - Có phân trang và tìm kiếm theo tên
  
![search](https://user-images.githubusercontent.com/85112203/132133641-acd6c0e5-a038-4cd9-b20c-6073d31e0d7b.JPG)

  
    - Các button Action sẽ thực hiện chức năng sửa, xóa

![list](https://user-images.githubusercontent.com/85112203/132133609-b487baef-397b-4cb3-94e8-9df20610e548.JPG)

#### Contact details
    - Khi User click vào email trong Contact list. Thông tin chi tiết về contact đó sẽ hiện lên
    - Trang thông tin chi tiết sẽ có thêm trường "Description" và "Work"
    
![details](https://user-images.githubusercontent.com/85112203/132133617-5be4093f-895c-47d6-bb2b-243c8d1e9f31.JPG)

#### Your Profile
    - Hiện thông tin user
    
![user](https://user-images.githubusercontent.com/85112203/132133619-9d805b5a-ef04-48ee-917f-19b88e52ad43.JPG)

#### Setting
    - Đổi mật khẩu
    
![change](https://user-images.githubusercontent.com/85112203/132133628-88f3cd23-f901-4166-8ceb-73b21b407ab0.JPG)

    - Khi đổi pass thành công, sẽ redirect tới trang home và thông báo đổi pass thành công
    
![changepass](https://user-images.githubusercontent.com/85112203/132133667-470c38e7-ced1-492c-97d0-c47bd060df28.JPG)


#### Logout
    - Logout và quay trở lại trang đăng nhập

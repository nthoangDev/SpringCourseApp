## Hệ thống Khóa Học Trực Tuyến

**Mô tả:**  
Hệ thống cho phép người dùng tìm kiếm, đăng ký và tham gia các khóa học trực tuyến, đồng thời theo dõi tiến độ học tập, nộp bài tập/quiz, nhận chứng chỉ và đánh giá sau khi hoàn thành khóa học. Quản trị viên và giảng viên có thể quản lý toàn bộ nội dung, tạo bài giảng, bài kiểm tra và chấm điểm.

### Tính năng chính

- **Quản lý người dùng & phân quyền**  
  - Đăng ký, đăng nhập, phân quyền ADMIN / INSTRUCTOR / STUDENT  
  - JWT authentication & role-based access control  

- **Quản lý khóa học**  
  - CRUD Course, Category, Lesson, Resource  
  - Tìm kiếm & lọc theo từ khóa, giảng viên, đánh giá, thời lượng  

- **Giỏ hàng & thanh toán**  
  - Thêm/xóa khóa học vào giỏ hàng  
  - Simulate payment (VNPAY, Momo, Stripe, …)  

- **Theo dõi tiến độ & bài tập**  
  - Hiển thị tỉ lệ hoàn thành khóa học  
  - Nộp bài tập (file upload), làm quiz trắc nghiệm  
  - Giảng viên chấm điểm & phản hồi  

- **Đánh giá & chứng chỉ**  
  - Học viên đánh giá, nhận xét khóa học  
  - Tự động sinh PDF chứng chỉ khi hoàn thành  
  - Tải về hoặc chia sẻ chứng chỉ  

- **Thông báo & nhắc nhở**  
  - Gửi email nhắc hạn nộp bài, sự kiện mới bằng Spring Scheduler  

### Công nghệ sử dụng

- **Backend:** Spring MVC, Spring Security, MySQL
- **Frontend:** ReactJS, React Router, React Context, Axios, Bootstrap


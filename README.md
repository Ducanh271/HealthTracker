# HealthTracker

Ứng dụng Android quản lý sức khỏe & theo dõi calo. Người dùng thiết lập hồ sơ cá nhân, ghi lại bữa ăn và hoạt động thể chất mỗi ngày, từ đó theo dõi lượng calo nạp vào / tiêu thụ và duy trì mục tiêu cân nặng.

## Tính năng

- **Hồ sơ & TDEE**: nhập thông tin cá nhân, tự tính tuổi và TDEE (BMR × hệ số vận động, điều chỉnh theo mục tiêu giảm/giữ/tăng cân).
- **Nhật ký bữa ăn**: chọn ngày, thêm món từ danh mục có sẵn hoặc tự nhập, tính calo từng món và tổng calo trong ngày, xóa món.
- **Nhật ký hoạt động**: thêm hoạt động, tính calo tiêu thụ theo công thức `MET × cân nặng × thời gian`, gợi ý nhanh 5 hoạt động dùng gần đây.
- **Dashboard**: vòng tròn tiến độ calo, thẻ tổng hợp (nạp / tiêu thụ / cân bằng), lời khuyên theo tình trạng, biểu đồ cột 7 ngày và biểu đồ đường xu hướng theo tuần.
- **Chỉ số BMI**: tính và phân loại (thiếu cân / bình thường / thừa cân / béo phì).
- **Cài đặt**: chỉnh sửa hồ sơ, đổi ngôn ngữ (Tiếng Việt / English), giao diện sáng–tối–theo hệ thống, nhiều bộ màu, cỡ chữ.
- **Thông báo nhắc**: nhắc ghi nhật ký lúc 7h / 12h / 19h, có công tắc bật/tắt.
- **Widget màn hình chính**: hiển thị vòng tròn tiến độ calo trong ngày.

## Công nghệ

- **Ngôn ngữ**: Kotlin (Coroutines, Flow / StateFlow / SharedFlow)
- **UI**: Jetpack Compose + Material 3
- **Kiến trúc**: Clean Architecture (UI – Domain – Data) + MVVM
- **DI**: Hilt
- **Lưu trữ**: Room (nhật ký & danh mục món/hoạt động), DataStore Preferences (hồ sơ & cài đặt)
- **Điều hướng**: Navigation Compose
- **Widget**: Glance

Min SDK 29 · Target SDK 37 · Java 11.

## Kiến trúc

Code chia 3 lớp dưới `com.example.healthtracker`:

- **`domain/`** — Kotlin thuần: model & enum, interface repository, use case (mỗi class một nghiệp vụ). Không phụ thuộc Android/Compose.
- **`data/`** — Room + DataStore, hiện thực repository của domain, mapper entity ↔ domain.
- **`ui/`** — các màn hình Compose theo feature (`onboarding`, `dashboard`, `diary`, `profile`, `settings`), mỗi màn có `*Screen`, `*ViewModel` và hợp đồng State / Event.

Ngoài ra: `notification/` (nhắc nhở), `widget/` (Glance), `di/` (module Hilt), `utils/`.

## Chạy dự án

```bash
./gradlew assembleDebug     # build APK debug
./gradlew installDebug      # cài lên thiết bị/emulator đang kết nối
./gradlew test              # unit test
./gradlew lint              # kiểm tra lint
```

APK sau khi build nằm ở `app/build/outputs/apk/debug/app-debug.apk`.

> Lưu ý: danh mục món ăn & hoạt động (tiếng Việt) được nạp sẵn vào database ở lần chạy đầu tiên.

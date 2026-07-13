package com.example.healthtracker.data.local.room

import com.example.healthtracker.data.local.room.entity.ActivityItemEntity
import com.example.healthtracker.data.local.room.entity.FoodItemEntity

object SeedData {
    val predefinedFoods = listOf(
        FoodItemEntity(name = "Cơm trắng", servingSize = "100g", calories = 130),
        FoodItemEntity(name = "Trứng gà luộc", servingSize = "1 quả", calories = 78),
        FoodItemEntity(name = "Phở bò (tô nhỏ)", servingSize = "1 tô", calories = 350),
        FoodItemEntity(name = "Bánh mì thịt", servingSize = "1 ổ", calories = 265),
        FoodItemEntity(name = "Bún chả", servingSize = "1 phần", calories = 450),
        FoodItemEntity(name = "Xôi xéo", servingSize = "1 gói", calories = 400),
        FoodItemEntity(name = "Cơm rang dưa bò", servingSize = "1 đĩa", calories = 600),
        FoodItemEntity(name = "Bún bò Huế", servingSize = "1 tô", calories = 475),
        FoodItemEntity(name = "Bánh cuốn thịt", servingSize = "1 đĩa", calories = 300),
        FoodItemEntity(name = "Mì tôm (thêm trứng)", servingSize = "1 bát", calories = 420),
        FoodItemEntity(name = "Gà rán (phần đùi)", servingSize = "1 miếng", calories = 280),
        FoodItemEntity(name = "Khoai lang luộc", servingSize = "100g", calories = 86),
        FoodItemEntity(name = "Ngô luộc", servingSize = "1 bắp", calories = 120),
        FoodItemEntity(name = "Thịt lợn luộc", servingSize = "100g", calories = 242),
        FoodItemEntity(name = "Thịt bò xào", servingSize = "100g", calories = 250),
        FoodItemEntity(name = "Cá chép rán", servingSize = "100g", calories = 279),
        FoodItemEntity(name = "Đậu phụ rán", servingSize = "1 bìa", calories = 110),
        FoodItemEntity(name = "Canh mùng tơi", servingSize = "1 bát", calories = 25),
        FoodItemEntity(name = "Rau muống xào tỏi", servingSize = "1 đĩa", calories = 115),
        FoodItemEntity(name = "Salad trộn mayonnaise", servingSize = "1 đĩa", calories = 150),
        FoodItemEntity(name = "Táo", servingSize = "1 quả (vừa)", calories = 52),
        FoodItemEntity(name = "Chuối", servingSize = "1 quả", calories = 89),
        FoodItemEntity(name = "Cam", servingSize = "1 quả", calories = 47),
        FoodItemEntity(name = "Dưa hấu", servingSize = "1 miếng (100g)", calories = 30),
        FoodItemEntity(name = "Sữa tươi không đường", servingSize = "1 hộp (180ml)", calories = 110),
        FoodItemEntity(name = "Sữa chua có đường", servingSize = "1 hộp", calories = 100),
        FoodItemEntity(name = "Trà sữa (size M)", servingSize = "1 ly", calories = 450),
        FoodItemEntity(name = "Cà phê đen (thêm đường)", servingSize = "1 ly", calories = 20),
        FoodItemEntity(name = "Bánh quy (Cosy)", servingSize = "3 cái", calories = 140),
        FoodItemEntity(name = "Chè đậu đen", servingSize = "1 cốc", calories = 250)
    )

    val predefinedActivities = listOf(
        ActivityItemEntity(name = "Đi bộ", metValue = 3.5f),
        ActivityItemEntity(name = "Chạy bộ", metValue = 9.8f),
        ActivityItemEntity(name = "Đạp xe", metValue = 7.5f),
        ActivityItemEntity(name = "Bơi lội", metValue = 8.0f),
        ActivityItemEntity(name = "Yoga", metValue = 3.0f),
        ActivityItemEntity(name = "Gym (tập tạ)", metValue = 6.0f),
        ActivityItemEntity(name = "Leo cầu thang", metValue = 8.0f),
        ActivityItemEntity(name = "Nhảy dây", metValue = 10.0f),
        ActivityItemEntity(name = "Cầu lông", metValue = 5.5f),
        ActivityItemEntity(name = "Bóng đá", metValue = 9.0f),
        ActivityItemEntity(name = "Bóng rổ", metValue = 8.0f),
        ActivityItemEntity(name = "Tennis", metValue = 7.3f),
        ActivityItemEntity(name = "Aerobic", metValue = 7.3f),
        ActivityItemEntity(name = "Võ thuật (Boxing/Karate)", metValue = 10.0f),
        ActivityItemEntity(name = "Dọn dẹp nhà cửa", metValue = 3.5f)
    )
}
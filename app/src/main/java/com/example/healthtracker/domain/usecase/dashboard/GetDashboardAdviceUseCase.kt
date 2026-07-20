package com.example.healthtracker.domain.usecase.dashboard

import javax.inject.Inject

// Tạo UseCase mới
class GetDashboardAdviceUseCase @Inject constructor() {
    operator fun invoke(consumed: Int, remaining: Int): String {
        return when {
            consumed == 0 -> "Bắt đầu ngày mới! Hãy ghi lại bữa ăn đầu tiên."
            remaining < 0 -> "Cảnh báo: Bạn đã vượt quá lượng calo mục tiêu!"
            remaining < 200 -> "Tuyệt vời! Bạn đã sắp đạt mục tiêu."
            else -> "Bạn đang làm rất tốt! Hãy tiếp tục nhé."
        }
    }
}
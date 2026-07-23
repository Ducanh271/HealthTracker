package com.example.healthtracker.domain.usecase.dashboard

import com.example.healthtracker.domain.model.AdviceType
import javax.inject.Inject

class GetDashboardAdviceUseCase @Inject constructor() {
    operator fun invoke(consumed: Int, remaining: Int): AdviceType {
        return when {
            consumed == 0 -> AdviceType.START_DAY
            remaining < 0 -> AdviceType.EXCEEDED
            remaining < 200 -> AdviceType.ALMOST_THERE
            else -> AdviceType.KEEP_GOING
        }
    }
}
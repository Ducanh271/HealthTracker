package com.example.healthtracker.data.mapper

import com.example.healthtracker.data.local.room.entity.MealLogEntity
import com.example.healthtracker.domain.model.Meal

fun MealLogEntity.toDomain(): Meal {
    return Meal(
        id = this.id,
        date = this.date,
        mealType = this.mealType,
        foodName = this.foodName,
        servingCount = this.servingCount,
        totalCalories = this.totalCalories
    )
}

fun Meal.toEntity(): MealLogEntity {
    return MealLogEntity(
        id = this.id,
        date = this.date,
        mealType = this.mealType,
        foodName = this.foodName,
        servingCount = this.servingCount,
        totalCalories = this.totalCalories
    )
}
package com.example.healthtracker.data.mapper

import com.example.healthtracker.data.local.room.entity.FoodItemEntity
import com.example.healthtracker.data.local.room.entity.MealLogEntity
import com.example.healthtracker.domain.model.FoodItem
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

fun FoodItemEntity.toDomain(): FoodItem {
    return FoodItem(
        id = this.id,
        name = this.name,
        servingSize = this.servingSize,
        calories = this.calories
    )
}

fun FoodItem.toEntity(): FoodItemEntity {
    return FoodItemEntity(
        id = this.id,
        name = this.name,
        servingSize = this.servingSize,
        calories = this.calories
    )
}
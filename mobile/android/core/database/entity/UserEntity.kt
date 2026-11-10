package com.trinetra.mobile.android.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// User entity for Room database
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(val autoGenerate = false) val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null
)
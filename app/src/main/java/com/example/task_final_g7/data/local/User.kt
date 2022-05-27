package com.example.task_final_g7.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var fullName: String? = null,
    var dateOfBirth: String? = null,
    var address: String? = null,
    var imageProfile: String? = null
) : Parcelable
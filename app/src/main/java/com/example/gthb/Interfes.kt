package com.example.save_image_database.database

import com.example.gthb.ImageUser

interface Interfes {
    fun insertImage(imageUser: ImageUser)
    fun getAllImage():List<ImageUser>
}
package com.example.core.database.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.home.add_pet.model.Gender
import com.example.core.domain.home.add_pet.model.PetModel
import com.example.core.domain.home.add_pet.model.PetType
import java.time.LocalDate

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val type: PetType = PetType.DOG,
    val breed: String = "",
    val birthdate: LocalDate? = null,
    val gender: Gender = Gender.UNKNOWN,
    val weight: Float = 0f,
    val color: String = "",
    val microchipNumber: String = "",
    val imageUri: String = ""
)

fun PetEntity.toDomainModel(): PetModel =
    PetModel(
        name = name,
        type = type,
        breed = breed,
        birthdate = birthdate,
        gender = gender,
        weight = weight,
        color = color,
        microchipNumber = microchipNumber,
        imageUri = imageUri)
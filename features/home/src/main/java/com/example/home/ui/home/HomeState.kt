package com.example.home.ui.home

import com.example.core.domain.shared.model.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val upcomingEvents: List<PetEvent> = emptyList(),
    val petCount: Int? = null
)

data class PetEvent(
    val id: String,
    val title: String,
    val petName: String,
    val date: String,
    val type: EventType
)

enum class EventType {
    VET,
    MEDICATION,
    GROOMING,
    OTHER
}

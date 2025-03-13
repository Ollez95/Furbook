package com.example.home.ui.home

interface HomeEvent {
    object AddPetClicked : HomeEvent
    object VetVisitsClicked : HomeEvent
    object MedicationsClicked : HomeEvent
    object PetProfileClicked : HomeEvent
}
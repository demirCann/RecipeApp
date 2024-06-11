package com.demircandemir.reciper.presentation.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }
}
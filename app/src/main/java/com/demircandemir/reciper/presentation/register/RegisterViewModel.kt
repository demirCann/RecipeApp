package com.demircandemir.reciper.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            repository.registerUserWithEmailAndPassword(email, password) { success, error ->
                if (success) {
                    _registerState.value = RegisterState(success = true)
                } else {
                    _registerState.value = RegisterState(error = error)
                }
            }
        }
    }
}

data class RegisterState(
    val success: Boolean = false,
    val error: String? = null
)
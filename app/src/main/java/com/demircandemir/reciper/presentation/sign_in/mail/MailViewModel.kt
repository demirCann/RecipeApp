package com.demircandemir.reciper.presentation.sign_in.mail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
): ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.signInWithEmailAndPassword(email, password) { success, error ->
                if (success) {
                    _loginState.value = LoginState(success = true)
                } else {
                    _loginState.value = LoginState(error = error)
                }
            }
        }
    }
}

data class LoginState(
    val success: Boolean = false,
    val error: String? = null
)
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignupViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signup(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email and password must not be empty"
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _signupResult.value = true
            } else {
                _error.value = task.exception?.message ?: "Unknown error occurred"
            }
        }
    }
}

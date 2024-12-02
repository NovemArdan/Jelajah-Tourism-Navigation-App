import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingsViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun logout(onComplete: () -> Unit) {
        try {
            firebaseAuth.signOut()
            Log.d("SettingsViewModel", "Logged out successfully.")
            onComplete()
        } catch (e: Exception) {
            Log.e("SettingsViewModel", "Logout failed: ${e.message}")
        }
    }

}

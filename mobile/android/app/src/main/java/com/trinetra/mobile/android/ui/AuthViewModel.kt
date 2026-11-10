@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: TRINETRAApiService,
    @ApplicationContext private val context: Context,
    private val dataStore: AppPreferencesDataStore
) : ViewModel() {

    // Auth state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Uninitialized)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Auth token from DataStore
    private val _authToken = dataStore.readAuthToken(context)
    val authToken: Flow<String?> = _authToken

    // Current user
    private val _currentUser = dataStore.readAuthToken(context).map { parseUserFromToken(it) }
    val currentUser: Flow<User?> = _currentUser

    // Loading state
    private var _isLoading = true
    val isLoading: Boolean get() = _isLoading

    // Error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Login function
    suspend fun login(username: String, password: String) {
        _isLoading = true
        _errorMessage.value = null

        val credentials = LoginCredentials(username, password)

        try {
            val response = apiService.login(credentials)
            if (response.success && response.data != null) {
                // Save auth token to DataStore
                dataStore.writeAuthToken(context, response.data!!.token)
                _authState.value = AuthState.LoggedIn(response.data!!.user)
                _errorMessage.value = null
            } else {
                _errorMessage.value = response.error ?: "Login failed"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Network error. Please try again."
        } finally {
            _isLoading = false
        }
    }

    // Register function
    suspend fun register(username: String, email: String, password: String) {
        _isLoading = true
        _errorMessage.value = null

        val credentials = RegisterCredentials(username, email, password)

        try {
            val response = apiService.register(credentials)
            if (response.success && response.data != null) {
                // Save auth token to DataStore
                dataStore.writeAuthToken(context, response.data!!.token)
                _authState.value = AuthState.Registered(response.data!!.user)
                _errorMessage.value = null
            } else {
                _errorMessage.value = response.error ?: "Registration failed"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Network error. Please try again."
        } finally {
            _isLoading = false
        }
    }

    // Logout function
    suspend fun logout() {
        dataStore.writeAuthToken(context, null)
        _authState.value = AuthState.Uninitialized
        _errorMessage.value = null
    }

    // Initialize auth state
    @Composable
    fun initAuth() {
        lifecycleScope.launch {
            _isLoading = true
            try {
                // Check if we have a saved token
                val token = dataStore.readAuthToken(context).first()
                if (token != null) {
                    _authState.value = AuthState.LoggedIn(parseUserFromToken(token))
                } else {
                    _authState.value = AuthState.Uninitialized
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Uninitialized
            } finally {
                _isLoading = false
            }
        }
    }

    // Parse user from token (simple placeholder)
    private fun parseUserFromToken(token: String): User {
        // In a real app, decode the JWT token to get user info
        return User(
            id = "user_123",
            name = "TRINETRA User",
            email = "user@trinetra.com"
        )
    }
}

// Auth states
enum class AuthState {
    Uninitialized,
    LoggedIn(User),
    Registered(User),
    Loading,
    Error(String)
}

// Data classes for UI
data class User(
    val id: String,
    val name: String,
    val email: String
)
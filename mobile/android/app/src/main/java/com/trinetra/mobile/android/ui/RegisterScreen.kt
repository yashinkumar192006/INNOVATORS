@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit = { }
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TRINETRA Register",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (error != null) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(email = true),
            supportingText = {
                if (error != null) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isPassword = true,
            supportingText = {
                if (error != null) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Confirm Password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isPassword = true,
            supportingText = {
                if (error != null) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        // Register button
        if (!loading) {
            Button(
                onClick = {
                    // Validation
                    var validationError: String? = null
                    
                    if (username.isEmpty()) {
                        validationError = "Username is required"
                    } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        validationError = "Please enter a valid email"
                    } else if (password.isEmpty() || password.length < 8) {
                        validationError = "Password must be at least 8 characters"
                    } else if (password != confirmPassword) {
                        validationError = "Passwords do not match"
                    }
                    
                    if (validationError != null) {
                        error = validationError
                    } else {
                        viewModel.register(username, email, password)
                        if (viewModel.errorMessage.value != null) {
                            error = viewModel.errorMessage.value
                        } else {
                            onRegisterSuccess()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !username.isEmpty() && !email.isEmpty() && !password.isEmpty()
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            ) {
                Outlined(
                    progressIcon = {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                ) {
                    Text("Registering...")
                }
            }
        }

        // Login link
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            TextButton(
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Text("Already have an account? Login")
            }
        }
    }
}
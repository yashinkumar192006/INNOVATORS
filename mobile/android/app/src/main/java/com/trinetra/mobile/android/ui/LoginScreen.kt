@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = { }
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            text = "TRINETRA Login",
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

        // Login button
        if (!loading) {
            Button(
                onClick = {
                    viewModel.login(username, password)
                    if (viewModel.errorMessage.value != null) {
                        error = viewModel.errorMessage.value
                    } else {
                        // Login successful
                        onLoginSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !username.isEmpty() && !password.isEmpty()
            ) {
                Text(
                    text = "Login",
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
                    Text("Logging in...")
                }
            }
        }

        // Register link
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            TextButton(
                onClick = {
                    navController.navigate("register")
                }
            ) {
                Text("Create account")
            }
        }
    }
}
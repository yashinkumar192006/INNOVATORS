@Composable
fun SettingsScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        appBar = Toolbar(
            title = { Text("Settings") },
            actions = {
                Button(onClick = onLogout) {
                    Text("Logout", style = MaterialTheme.typography.button)
                }
            }
        )
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Theme selection
            outlinedTextField(
                value = "Dark Mode",
                onValueChange = {},
                label = { Text("Theme") },
                trailingIcon = {
                    Icon(Icons.Default.DarkMode, contentDescription = null)
                }
            )

            // Language selection
            outlinedTextField(
                value = "English",
                onValueChange = {},
                label = { Text("Language") },
                trailingIcon = {
                    Icon(Icons.Default.Language, contentDescription = null)
                }
            )

            // Divider
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Logout button
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
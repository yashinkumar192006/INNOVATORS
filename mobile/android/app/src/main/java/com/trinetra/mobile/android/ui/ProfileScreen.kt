@Composable
fun ProfileScreen(
    user: User,
    onLogout: () -> Unit
) {
    Scaffold(
        appBar = Toolbar(
            title = { Text("Profile") },
            actions = {
                Button(
                    onClick = onLogout
                ) {
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
            // User avatar/initials
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${user.name.substring(0, 1).toUpperCase()}",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // User info
            Text(
                text = user.name,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Profile actions
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { /* Edit profile */ }
            ) {
                Text("Edit Profile")
            }

            OutlinedButton(
                onClick = { onLogout() }
            ) {
                Text("Logout", style = MaterialTheme.typography.button2)
            }
        }
    }
}
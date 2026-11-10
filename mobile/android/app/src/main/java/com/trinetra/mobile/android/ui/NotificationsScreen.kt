@Composable
fun NotificationsScreen() {
    Scaffold(
        appBar = Toolbar(
            title = { Text("Notifications") },
        )
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Notifications list placeholder
            Box(
                modifier = Modifier.fillMaxWeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No new notifications",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
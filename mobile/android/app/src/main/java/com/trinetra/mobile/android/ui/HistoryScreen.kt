@Composable
fun HistoryScreen() {
    Scaffold(
        appBar = Toolbar(
            title = { Text("History") },
            navigationIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
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
                text = "Investigation History",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Placeholder for history list
            Box(
                modifier = Modifier.fillMaxWeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No investigation history yet",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
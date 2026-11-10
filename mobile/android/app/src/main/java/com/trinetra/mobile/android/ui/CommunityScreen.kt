@Composable
fun CommunityScreen() {
    Scaffold(
        appBar = Toolbar(
            title = { Text("Community") },
        )
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Community",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Community posts or forums placeholder
            Box(
                modifier = Modifier.fillMaxWeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Community features coming soon",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
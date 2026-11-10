@Composable
fun InvestigationScreen(
    navController: NavHostController,
    viewModel: InvestigationViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var results by remember { mutableStateOf<List<InvestigationResult>>() }
    var loading by remember { mutableStateOf(true) )
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    // Start new investigation
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search investigation") },
                placeholder = { Text("Enter query or identifier") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (!searchQuery.isEmpty()) {
                        Button(
                            onClick = {
                                searchQuery = ""
                            }
                        ) {
                            Text("Clear", style = MaterialTheme.typography.caption)
                        }
                    }
                }
            )

            // Results or loading
            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (error != null) {
                Text(
                    text = "Error: ${error}",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.error
                )
            } else if (results.isNotEmpty()) {
                Text(
                    text = "Found ${results.size} results",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onSurface
                )
            } else {
                Text(
                    text = "No investigations found",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
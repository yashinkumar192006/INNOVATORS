@Composable
fun InvestigationCreationScreen(
    viewModel: InvestigationViewModel = hiltViewModel(),
    onInvestigationCreated: (InvestigationPool) -> Unit = { }
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) )
    var error by remember { mutableStateOf<String?>(null) }

    // Category options
    val categories = listOf(
        "MEDIA",
        "INFORMATION", 
        "IDENTITY",
        "INVESTIGATION",
        "HISTORY",
        "COMMUNITY",
        "NOTIFICATIONS"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterStart
    ) {
        // Title field
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Investigation Title") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (title.isNotEmpty()) {
                    IconButton(onClick = { title = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            }

            // Description field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            // Category selector
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = { selectedCategory = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            )

            // Error message
            if (error != null) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Submit button
            if (!loading) {
                Button(
                    onClick = {
                        if (title.isNotEmpty() && selectedCategory != null) {
                            loading = true
                            viewModel.createInvestigationPool(
                                title = title,
                                description = description,
                                category = selectedCategory
                            ).fold(
                                onInvestigationCreated::invoke,
                                { error = it }
                            )
                        }
                    )
                } {
                    Text("Create Investigation Pool")
                }
            } else {
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), enabled = false) {
                    Outlined(
                        progressIcon = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    ) {
                        Text("Creating...")
                    }
                }
            }

            // Cancel
            OutlinedButton(
                onClick = { /* navigate back */ }
            ) {
                Text("Cancel")
            }
        }
    }
}
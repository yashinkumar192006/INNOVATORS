@Composable
fun CommunityFeedScreen(
    viewModel: CommunityViewModel = hiltViewModel(),
    onPostCreated: (CommunityPost) -> Unit = { }
) {
    var filterText by remember { mutableStateOf("") }
    var showOnlyMyPosts by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) )
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    viewModel.createNewPost()
                    onPostCreated(viewModel.newPost.getValue()!!)
                }
            ) {
                Text("+ Investigate")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Filter and search
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filter by category or keyword") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (!filterText.isEmpty()) {
                        Button(onClick = { filterText = "" }) {
                            Text("Clear", style = MaterialTheme.typography.caption)
                        }
                    }
                }
            )

            // Posts list or loading
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
            } else {
                // Display posts from the feed
                if (viewModel.posts.isEmpty()) {
                    Text(
                        text = "No posts found",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    viewModel.posts.map { post ->
                        CommunityPostCard(
                            post = post,
                            onVoteClick = { viewModel.castVote(post.id) },
                            onCommentClick = { /* navigate to comments */ }
                        )
                    }
                }
            }
        }
    }
}
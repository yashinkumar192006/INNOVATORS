@Composable
fun CommunityPostCard(
    post: CommunityPost,
    onVoteClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 1.dp) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Author line
            Row(verticalAlignment = Alignment.Start) {
                Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center, defaultColor = MaterialTheme.colorScheme.secondary) {
                    Text(text = post.authorName.substring(0, 1).toUpperCase(), style = MaterialTheme.typography.body1, color = MaterialTheme.colorScheme.onSecondary)
                }
                Column(Modifier.weight(1f)) {
                    Text(post.authorName, style = MaterialTheme.typography.subtitle1, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(formatTimestamp(post.timestamp), style = MaterialTheme.typography.caption, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Content summary
            OutlinedTextField(value = post.evidenceSummary ?: "", onValueChange = {}, label = { Text("Content") }, isReadOnly = true, modifier = Modifier.fillMaxWidth())

            // Distinguish: Community opinion / Reviewer assessment / Moderator verification
            Row(Modifier.padding(top = 8.dp)) {
                // Community vote count
                OutlinedButton(modifier = Modifier.weight(0.33f), onClick = onVoteClick) {
                    Text("${post.communityVotes} votes")
                }
                // Reviewer assessment (if present)
                if (post.reviewerAssessment != null) {
                    OutlinedButton(modifier = Modifier.weight(0.33f)) {
                        Text("Reviewer: ${post.reviewerAssessment}", style = MaterialTheme.typography.caption)
                    }
                }
                // Moderator verification
                if (post.moderatorVerification) {
                    OutlinedButton(modifier = Modifier.weight(0.33f)) {
                        Text("✓ Verified", style = MaterialTheme.typography.caption)
                    }
                }
            }

            // Investigation match type
            when (post.matchType) {
                MatchResult.MatchType.EXACT -> OutlinedButton { Text("Exact Match - Verified", style = MaterialTheme.typography.caption) }
                MatchResult.MatchType.SIMILAR -> OutlinedButton { Text("Similar Historical Content", style = MaterialTheme.typography.caption) }
                MatchResult.MatchType.NONE -> Text("New Investigation", style = MaterialTheme.typography.caption, color = MaterialTheme.colorScheme.onSurface)
            }

            // Comments
            Row(Modifier.padding(top = 8.dp)) {
                Text("${post.comments?.size ?: 0} comments", style = MaterialTheme.typography.caption, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = onCommentClick, modifier = Modifier.padding(start = 4.dp)) {
                    Text("View", style = MaterialTheme.typography.caption)
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return format.format(date)
}
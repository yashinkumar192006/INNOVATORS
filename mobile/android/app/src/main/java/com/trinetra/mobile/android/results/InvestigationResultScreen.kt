@Composable
fun InvestigationResultScreen(
    result: InvestigationResult,
    onDismiss: () -> Unit = { }
) {
    Card(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterStart) {
            // Result type indicator
            Text(
                text = result.resultType.displayName,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Investigation ID
            OutlinedTextField(
                value = result.investigationId,
                onValueChange = {},
                label = { Text("Investigation ID") },
                isReadOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Verdict
            Text(
                text = "Verdict: ${result.verdict.displayName}",
                style = MaterialTheme.typography.body1,
                color = when (result.verdict) {
                    Verdict.CLEAN -> MaterialTheme.colorScheme.secondary
                    Verdict.THREAT -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Confidence when supported
            if (result.confidence != null) {
                Text(
                    text = "Confidence: ${(result.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Risk
            Text(
                text = "Risk: ${result.riskClassification.displayName}",
                style = MaterialTheme.typography.body1,
                color = when (result.riskClassification) {
                    RiskClassification.LOW -> MaterialTheme.colorScheme.secondary
                    RiskClassification.HIGH -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Content summary
            OutlinedTextField(
                value = result.contentSummary,
                onValueChange = {},
                label = { Text("Content Summary") },
                isReadOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Timestamp
            Text(
                text = "Processed: ${formatTimestamp(result.timestamp)}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}
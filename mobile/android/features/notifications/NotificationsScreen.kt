@Composable
fun NotificationsScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onDismiss: () -> Unit = { }
) {
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) )
    var notifications by remember { mutableStateOf<List<NotificationItem>>() }

    LaunchedEffect(Unit) {
        viewModel.loadNotifications().collect { items ->
            notifications.value = items
            loading = false
        }
    }

    Scaffold(
        bottomBar = BottomNavigationView(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            @ComposableItem(
                id = R.id.notifications_feed,
                label = { Text("Feed") },
                icon = { Icon(Icons.Default.Inbox, contentDescription = null) }
            ) {
                if (loading) {
                    CircularProgressIndicator()
                } else if (error != null) {
                    Text("Error: ${error}")
                } else if (notifications.isEmpty()) {
                    Text("No notifications")
                } else {
                    val expandedNotification = remember { mutableStateOf<NotificationItem?>(null) }
                    
                    val scrollState = rememberScrollState()
                    
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notifications) { notification ->
                            NotificationItemCard(
                                notification = notification,
                                onToggleExpanded = { expandedNotification.value = it },
                                onDismiss = { onDismiss() }
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterStart
        ) {
            when {
                loading -> CircularProgressIndicator()
                error != null -> Text("Error: ${error}")
                notifications.isEmpty() -> Text("No notifications")
                else -> Unit
            }
            if (expandedNotification.value != null) {
                ExpandedNotificationCard(
                    notification = expandedNotification.value,
                    onDismiss = { expandedNotification.value = null }
                )
            }
        }
    }
}
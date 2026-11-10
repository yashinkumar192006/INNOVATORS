@Composable
fun HomeDashboard(
    navController: NavHostController,
    user: User,
    onLogout: () -> Unit
) {
    Scaffold(
        bottomBar = BottomNavigationView(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            @ComposableItem(
                id = R.id.nav_dashboard,
                label = { Text("Dashboard") },
                icon = { Icon(Icons.Default.Dashboard, contentDescription = null) }
            ) {
                NavigationRoute(
                    navController = navController,
                    onLogout = onLogout
                )
            }

            @ComposableItem(
                id = R.id.nav_investigation,
                label = { Text("Investigation") },
                icon = { Icon(Icons.Default.Investigation, contentDescription = null) }
            ) {
                InvestigationScreen(
                    navController = navController,
                    onLogout = onLogout
                )
            }

            @ComposableItem(
                id = R.id.nav_history,
                label = { Text("History") },
                icon = { Icon(Icons.Default.History, contentDescription = null) }
            ) {
                HistoryScreen()
            }

            @ComposableItem(
                id = R.id.nav_community,
                label = { Text("Community") },
                icon = { Icon(Icons.Default.Community, contentDescription = null) }
            ) {
                CommunityScreen()
            }

            @ComposableItem(
                id = R.id.nav_notifications,
                label = { Text("Notifications") },
                icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
            ) {
                NotificationsScreen()
            }

            @ComposableItem(
                id = R.id.nav_profile,
                label = { Text("Profile") },
                icon = { Icon(Icons.Default.Profile, contentDescription = null) }
            ) {
                ProfileScreen(user = user, onLogout = onLogout)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "TRINETRA",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )

            // Welcome message
            Text(
                text = "Welcome, ${user.name}",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Main content placeholder
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Select a section from the bottom navigation",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
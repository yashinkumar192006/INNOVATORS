@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TRINETRA Logo
        Text(
            text = "TRINETRA",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        Text(
            text = "Cybersecurity Platform",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Animated loading indicator
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Subtitle
        Text(
            text = "Advanced threat investigation and identity verification",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 24.dp)
        )

        // Delay before navigating to login
        Delay(
            millis = 2000,
            onDone = {
                navController.navigate(
                    "login",
                    popUpTo("splash") { inclusive = true }
                )
            }
        )
    }
}
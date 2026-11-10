@Composable
fun UiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        darkTheme = darkTheme,
        colorScheme = when (darkTheme) {
            true -> ColorScheme(
                primaryColor = Color(0x2C3E50),
                secondaryColor = Color(0xE67E22),
                surfaceColor = Color(0x16213E),
                backgroundColor = Color(0x1A1A2E),
                onPrimaryColor = Color(0xECF0F1),
                onSurfaceColor = Color(0xECF0F1),
                onBackgroundColor = Color(0xECF0F1),
                secondaryVariant = Color(0xF39C12),
                surfaceVariant = Color(0x2C3E50),
                error = Color(0xE74C3C)
            )
            false -> ColorScheme(
                primaryColor = Color(0xECF0F1),
                secondaryColor = Color(0xE67E22),
                surfaceColor = Color(0xECF0F1),
                backgroundColor = Color(0x2C3E50),
                onPrimaryColor = Color(0x2C3E50),
                onSurfaceColor = Color(0x2C3E50),
                onBackgroundColor = Color(0x2C3E50),
                secondaryVariant = Color(0xF39C12),
                surfaceVariant = Color(0xECF0F1),
                error = Color(0xE74C3C)
            )
        }
    ) {
        content()
    }
}
package com.example.brofin.presentation.main.navigation.home


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(currentPage: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        bottomNavItems.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentPage == index,
                onClick = {
                    onTabSelected(index)
                }
            )
        }
    }
}

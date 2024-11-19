package com.example.brofin.presentation.main.navigation.home


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

//@Composable
//fun BottomNavigationBar(currentPage: Int, onTabSelected: (Int) -> Unit) {
//   Box {
//       NavigationBar(
//           modifier = Modifier
//               .fillMaxWidth()
//               .background(MaterialTheme.colorScheme.background),
//       ) {
//           bottomNavItems.forEachIndexed { index, screen ->
//               NavigationBarItem(
//                   icon = { Icon(screen.icon, contentDescription = screen.title) },
//                   label = { Text(screen.title) },
//                   selected = currentPage == index,
//                   onClick = {
//                       onTabSelected(index)
//                   }
//               )
//           }
//       }
//
//       FloatingActionButton(
//           onClick = { /*TODO*/ },
//           modifier = Modifier
//               .align(Alignment.TopCenter)
//               .offset(y = (-28).dp),
//           containerColor = Color(0xFFB39DDB),
//           shape = RoundedCornerShape(21.dp),
//       ) {
//           Icon(
//               imageVector = Icons.Default.Add,
//               contentDescription = "Add",
//               tint = Color.White
//           )
//       }
//   }
//}



@Composable
fun BottomNavigationBar(currentPage: Int, onTabSelected: (Int) -> Unit, onAddClick: () -> Unit) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    val disabledColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    // Use insets to avoid cutting the navigation bar at the bottom
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.navigationBars.asPaddingValues()) // Avoid navigation bar cut-off
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(backgroundColor)
        ) {
            // Canvas to create the curved bottom navigation with shadow
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val fabRadius = 40.dp.toPx()
                val fabCenterX = width / 2
                val curveWidth = fabRadius + 25.dp.toPx()
                val curveHeight = fabRadius * 0.6f // Adjust height to make the curve less sharp


                // Create path for the background with a curve in the middle
                val backgroundPath = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(fabCenterX - curveWidth, 0f)
                    cubicTo(
                        fabCenterX - curveWidth / 2, 0f,
                        fabCenterX - curveWidth / 2, -curveHeight,
                        fabCenterX, -curveHeight
                    )
                    cubicTo(
                        fabCenterX + curveWidth / 2, -curveHeight,
                        fabCenterX + curveWidth / 2, 0f,
                        fabCenterX + curveWidth, 0f
                    )
                    lineTo(width, 0f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                // Draw the background path with color
                drawPath(
                    path = backgroundPath,
                    color = backgroundColor
                )

                // Draw the outline of the curve following the background path
                drawPath(
                    path = backgroundPath,
                    color = Color.Gray.copy(alpha = 0.3f), // Adjust color and alpha as needed
                    style = Stroke(width = 2.dp.toPx())
                )
            }


            // Bottom Navigation Items
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                bottomNavItems.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = { Icon(
                            screen.icon,
                            contentDescription = screen.title,
                            tint = if (currentPage == index) Color.White else selectedColor
                        ) },
                        alwaysShowLabel = false,
                        label = {
                            Text(
                                screen.title,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.labelSmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        selected = currentPage == index,
                        onClick = { onTabSelected(index) },
                        colors = NavigationBarItemDefaults.colors().copy(
//                            selectedTextColor = selectedColor,
                            selectedIndicatorColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor,
                            disabledIconColor = disabledColor,
                            disabledTextColor = disabledColor
                        )
                    )
                    if (index == 1) {
                        Spacer(modifier = Modifier.width(63.dp))
                    }
                }
            }

            FloatingActionButton(
                onClick = { onAddClick() },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-16).dp)
                    .size(65.dp),
                containerColor = selectedColor,
                shape = RoundedCornerShape(27.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

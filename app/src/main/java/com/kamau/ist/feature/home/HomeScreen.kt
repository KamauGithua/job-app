package com.kamau.ist.feature.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kamau.ist.ui.theme.Purple80
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome User",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBotSheet() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current.applicationContext

    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(Purple80)
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Text(text = "IST ALUMNI APP")
                }

                Divider()

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Home,
                            contentDescription = "home",
                            modifier = Modifier.size(27.dp)
                        )
                    },
                    label = { Text(text = "Home", fontSize = 17.sp) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navController.navigate(Screens.Home.screen) {
                            popUpTo(0)
                        }
                    }
                )
                // Profile section
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(27.dp)
                        )
                    },
                    label = { Text(text = "Profile", fontSize = 17.sp) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navController.navigate(Screens.Profile.screen) {
                            popUpTo(0)
                        }
                    }
                )
                // Settings
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings",
                            modifier = Modifier.size(27.dp)
                        )
                    },
                    label = { Text(text = "Settings", fontSize = 17.sp) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navController.navigate(Screens.Settings.screen) {
                            popUpTo(0)
                        }
                    }
                )
                // Logout
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "logout",
                            modifier = Modifier.size(27.dp)
                        )
                    },
                    label = { Text(text = "Log Out", fontSize = 17.sp) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "IST ALUMNI APP") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 8.dp)
                                    .size(28.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(containerColor = Purple80) {
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Home
                            navController.navigate(Screens.Home.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                        )
                    }
                    // Search
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Search
                            navController.navigate(Screens.Search.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Search) Color.White else Color.DarkGray
                        )
                    }
                    // Floating Button
                    FloatingActionButton(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Purple80)
                    }
                    // Notifications
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Notifications
                            navController.navigate(Screens.Notifications.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Notifications) Color.White else Color.DarkGray
                        )
                    }
                    // JobList
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Info
                            navController.navigate(Screens.JobListScreen.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Info) Color.White else Color.DarkGray
                        )
                    }
                    // Profile
                    IconButton(
                        onClick = {
                            selected.value = Icons.Default.Person
                            navController.navigate(Screens.Profile.screen) {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected.value == Icons.Default.Person) Color.White else Color.DarkGray
                        )
                    }
                }
            }
        ) {
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        BottomSheetItem(
                            icon = Icons.Default.ThumbUp,
                            title = "Create a Post"
                        ) {
                            showBottomSheet = false
                            navController.navigate(Screens.Post.screen) {
                                popUpTo(0)
                            }
                        }
                        BottomSheetItem(
                            icon = Icons.Default.Star,
                            title = "Add a Job"
                        ) {
                            Toast.makeText(context, "Story", Toast.LENGTH_SHORT).show()
                        }
                        BottomSheetItem(
                            icon = Icons.Default.PlayArrow,
                            title = "Save a Job"
                        ) {
                            Toast.makeText(context, "Reels", Toast.LENGTH_SHORT).show()
                        }
                        BottomSheetItem(
                            icon = Icons.Default.Favorite,
                            title = "Go To My Network"
                        ) {
                            Toast.makeText(context, "Live", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(icon, contentDescription = null)
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}

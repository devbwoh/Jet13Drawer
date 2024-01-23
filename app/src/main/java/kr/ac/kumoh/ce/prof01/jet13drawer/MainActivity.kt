package kr.ac.kumoh.ce.prof01.jet13drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.prof01.jet13drawer.ui.theme.Jet13DrawerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jet13DrawerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainDrawer()
    }
}

@Composable
fun MainDrawer() {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val navController = rememberNavController()

    var title by rememberSaveable {
        mutableStateOf("노래")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawerSheet(drawerState) {
                navController.navigate(it) {
                    popUpTo("노래") { inclusive = true }
                    launchSingleTop = true
                }
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                MainTopBar(title, drawerState)
            },
            bottomBar = {
                MainBottomNavigation {
                    navController.navigate(it) {
                        popUpTo("노래") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = "노래",
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                composable("노래") {
                    title = "노래"
                    SongScreen()
                }
                composable("가수") {
                    title = "가수"
                    SingerScreen()
                }
            }
        }
    }
}

@Composable
fun MainDrawerSheet(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    fun close() {
        scope.launch {
            drawerState.close()
        }
    }

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = {
                Icon(Icons.Default.Favorite, "노래 아이콘")
            },
            label = { Text("노래") },
            selected = false,
            onClick = {
                onNavigate("노래")
                close()
            }
        )
        NavigationDrawerItem(
            icon = {
                Icon(Icons.Default.AccountCircle, "가수 아이콘")
            },
            label = { Text("가수") },
            selected = false,
            onClick = {
                onNavigate("가수")
                close()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Menu,
                    "메뉴 아이콘"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun MainBottomNavigation(onNavigate: (String) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        NavigationBarItem(
            icon = {
                Icon(Icons.Default.Favorite, "노래 아이콘")
            },
            label = {
                Text("노래")
            },
            selected = false,
            onClick = { onNavigate("노래") },
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Default.AccountCircle, "가수 아이콘")
            },
            label = {
                Text("가수")
            },
            selected = false,
            onClick = { onNavigate("가수") },
        )    }
}

@Composable
fun SongScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("노래", fontSize = 30.sp)
    }
}

@Composable
fun SingerScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("가수", fontSize = 30.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Jet13DrawerTheme {
        //MainScreen()
        MainDrawer()
    }
}
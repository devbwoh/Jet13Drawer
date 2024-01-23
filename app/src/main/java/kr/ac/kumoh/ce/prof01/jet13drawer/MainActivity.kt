package kr.ac.kumoh.ce.prof01.jet13drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        //initialValue = DrawerValue.Closed
        initialValue = DrawerValue.Open
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawerSheet(drawerState)
        },
        gesturesEnabled = true,
    ) {

    }
}

@Composable
fun MainDrawerSheet(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            icon = {
                Icon(Icons.Default.Favorite, "노래 아이콘")
            },
            label = { Text("노래") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
        NavigationDrawerItem(
            icon = {
                Icon(Icons.Default.AccountCircle, "가수 아이콘")
            },
            label = { Text("가수") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
            }
        )
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
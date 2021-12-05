package com.nvest.ecommerce.ui.dashboard

import DashboardItem
import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nvest.ecommerce.R
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.ui.base.BaseComponentActivity
import com.nvest.ecommerce.util.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : BaseComponentActivity<DashboardViewModel>() {


    override val viewModel: DashboardViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun ProvideCompose() {
        DashboardCompose {
            Scaffold(
                topBar = { TopBar() },
                bottomBar = {
                    AddBottomBar()
                }
            ) {
                var itemsIndexedList: List<DashboardResponseItem> by remember {
                    mutableStateOf(emptyList())
                }

                var msg = ""

                observe(viewModel.dashboardDetailsData) {
                    when (it) {
                        is Success -> {
                            itemsIndexedList = it.data!!
                        }
                        is DataError -> {
                            msg = it.errorDescription
                        }
                    }
                }
                ChildCompose(msg, itemsIndexedList)


            }

        }
    }

    @Composable
    private fun ChildCompose(
        msg: String,
        dashboardItemList: List<DashboardResponseItem>
    ) {

        if (dashboardItemList.isEmpty()) {

            Text(msg)

        } else {
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LazyColumn(
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.dp_56))
                    .testTag(
                        stringResource(R.string.dashboard_list_tag)
                    ),
                state = listState
            ) {

                itemsIndexed(dashboardItemList) { index, item ->

                    DashboardItem(item)

                }
                coroutineScope.launch {
                    // Animate scroll to the 10th item
                    listState.animateScrollToItem(index = 0)
                }
            }

        }


    }


    @ExperimentalMaterialApi
    @Composable
    private fun TopBar() {

        TopAppBar(title = {
            Text(modifier=Modifier.testTag(getString(R.string.dashboard_content_tag)),
                text = stringResource(R.string.dashboard),
                color = MaterialTheme.colors.onSecondary
            )
        },
            modifier = Modifier.background(
                Brush.horizontalGradient(
                    listOf(
                       MaterialTheme.colors.primary,
                       MaterialTheme.colors.secondary,

                        )
                )
            ),
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            actions = {
                var showMenu by remember { mutableStateOf(false) }

                IconButton(onClick = { /*TODO*/ }) {
                    BadgeBox(badgeContent = {
                        Text("8")
                    }) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                }
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_filter_list_24),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colors.onSecondary
                    )
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            showMenu = false
                            viewModel.filterByName()
                        }) {
                            Text(text = "Filter by name")
                        }
                        DropdownMenuItem(onClick = {
                            showMenu = false
                            viewModel.filterByPrice()

                        }) {
                            Text(text = "Filter by Price")
                        }
                    }
                }

            })
    }

    @Composable
    private fun DashboardCompose(childCompose: @Composable () -> Unit) {

        childCompose()

    }

    @Composable
    private fun AddBottomBar() {

        BottomNavigation(
            modifier = Modifier.background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.secondary,

                        )
                )
            )
        ) {
            BottomNavigationItem(label = { Text(text = "Home") },
                selectedContentColor = MaterialTheme.colors.onSecondary,
                icon = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colors.onSecondary

                    )
                },
                selected = false,
                onClick = {})


        }
    }

    @ExperimentalMaterialApi
    @Preview(
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_NO
    )
    @Composable
    override fun ProvideComposeLightPreview() {
        DashboardCompose {
            Scaffold(
                topBar = { TopBar() },
                bottomBar = {
                    AddBottomBar()
                }
            ) {

                ChildCompose("", emptyList<DashboardResponseItem>())

            }

        }

    }

}
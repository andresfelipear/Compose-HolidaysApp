package com.aarevalo.holidays.screens.common.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aarevalo.holidays.R
import com.aarevalo.holidays.domain.model.DrawerItem
import com.aarevalo.holidays.navigation.Route

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    drawerItems: List<DrawerItem>,
    onItemClick: (Route) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.95f)
    ) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .fillMaxHeight()
                .fillMaxWidth(0.9f),
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, top = 48.dp)
                    .fillMaxSize()
            ) {
                Column{
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = {
                                Text(stringResource(id = item.title))
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            selected = false,
                            onClick = { onItemClick(item.route) },
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
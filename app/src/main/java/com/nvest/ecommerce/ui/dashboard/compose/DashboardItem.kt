import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nvest.ecommerce.R
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.ui.theme.EcommerceTheme
import com.nvest.ecommerce.ui.theme.Shapes

@Composable
fun DashboardItem(item: DashboardResponseItem) {

    Card(
        border = BorderStroke(
            dimensionResource(R.dimen.dp_1),
            MaterialTheme.colors.primary
        ),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dp_20))
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = Shapes.medium

    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dp_10))
        ) {

            Row {
                Image(
                    painter  = rememberImagePainter(
                        data = item.image,
                        builder = {
//                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.dp_150))
                        .height(dimensionResource(R.dimen.dp_200))
                        .clip(Shapes.medium),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(
                    modifier = Modifier.width(
                        dimensionResource(
                            R.dimen.dp_10
                        )
                    )
                )

                Column(
                    Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = item.title ?: "",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(
                        modifier = Modifier.height(
                            dimensionResource(
                                R.dimen.dp_10
                            )
                        )
                    )

                    Text(
                        text = item.description ?: "",
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.caption,
                        maxLines = 4
                    )

                    Spacer(
                        modifier = Modifier.height(
                            dimensionResource(
                                R.dimen.dp_10
                            )
                        )
                    )

                    Text(
                        text = " ₹ " + item.price,
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body2
                    )

                    Row(
                        Modifier.padding(
                            top = dimensionResource(R.dimen.dp_10)
                        )
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.dp_30))
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "content description",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(
                                    dimensionResource(
                                        R.dimen.dp_25
                                    )
                                )
                            )
                        }
                        Text(
                            modifier = Modifier.padding(
                                start = dimensionResource(R.dimen.dp_5),
                                end = dimensionResource(R.dimen.dp_5)
                            ),
                            text = "1",
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.body2
                        )
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.dp_30))
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "content description",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(
                                    dimensionResource(
                                        R.dimen.dp_25
                                    )
                                )
                            )
                        }
                    }

                }


            }


        }
    }

}

@Preview(
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun DashboardPreview() {

    EcommerceTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            DashboardItem(DashboardResponseItem())
        }
    }

}
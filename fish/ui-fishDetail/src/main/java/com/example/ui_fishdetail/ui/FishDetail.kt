package com.example.ui_fishdetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.components.DefaultScreenUI
import com.example.fish_domain.Fish
import com.example.ui_fishdetail.R
import org.jsoup.Jsoup

@Composable
fun FishDetail(
    state: FishDetailState,
    imageLoader: ImageLoader,
    events : (FishDetailEvents) -> Unit,
) {

    DefaultScreenUI(
        progressBarState = state.progressBarState,
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(FishDetailEvents.OnRemoveHeadFromQueue)
        }
    ) {
        state.fish?.let { fish ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                item {
                    Column {
                        val painter = rememberImagePainter(
                            fish.speciesIllustrationPhoto?.src,
                            imageLoader = imageLoader,
                            builder = {
                                placeholder(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background)
                            }
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp),
                            painter = painter,
                            contentDescription = fish.scientificName,
                            contentScale = ContentScale.Fit,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                fish.speciesName?.let { fishSpeciesName ->
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(end = 8.dp),
                                        text = fishSpeciesName,
                                        style = MaterialTheme.typography.h1,
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                text = "Scientific Name : ${fish.scientificName}",
                                style = MaterialTheme.typography.subtitle1,
                            )
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 12.dp),
                                text = "Physical Description: ${html2text(fish.physicalDescription)}",
                                style = MaterialTheme.typography.subtitle1,
                            )
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 12.dp),
                                text = "Taste: ${html2text(fish.taste)}",
                                style = MaterialTheme.typography.subtitle1,
                            )
                            FishBaseStats(
                                fish = fish,
                                padding = 10.dp,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            //WinPercentages(hero = hero,)
                        }
                    }
                }
            }
        }

    }
}

// to convert html to string..
fun html2text(html: String?): String {
    return Jsoup.parse(html).text()
}

@Composable
fun FishBaseStats(
    fish: Fish,
    padding: Dp,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Fish Stats",
                style = MaterialTheme.typography.h4,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(end = 20.dp)
                ) { // Str, Agi, Int, Health
                    Row( // STR
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.calories)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${fish.calories}",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                    Row( // AGI
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.cholesterol)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${fish.cholesterol}",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                    Row( // INT
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.protein)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Row {
                            Text(
                                text = "${fish.protein}",
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                    Row( // HP
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${stringResource(R.string.sodium)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "${fish.sodium}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) { // Atk Range, proj speed, move speed, atk dmg
                    Row( // Atk Range
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.serving_weight)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${fish.servingWeight}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // projectile speed
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.carbohydrate)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${fish.carbohydrate}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // Move speed
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.sugars_total)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${fish.sugarsTotal}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Row( // Attack damage
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${stringResource(R.string.fat_total)}:",
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 8.dp),
                            text = "${fish.fatTotal}",
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }
}
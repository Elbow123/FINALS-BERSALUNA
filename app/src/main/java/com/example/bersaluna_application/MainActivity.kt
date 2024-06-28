package com.example.bersaluna_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

// Porsche model
data class PorscheModel(
    val name: String,
    val imageResId: Int,
    val description: String,
    var isFavorite: Boolean = false
)

// Display a list of Porsche models
@Composable
fun PorscheList(porscheModels: List<PorscheModel>) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            PorscheHeader()
            SearchBar(
                value = searchText,
                onValueChange = { searchText = it }
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val filteredModels = porscheModels.filter {
                    it.name.contains(searchText.text, ignoreCase = true)
                }
                items(filteredModels) { model ->
                    PorscheItem(model = model)
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(listState)
        )
    }
}

// Display header for Porsche list
@Composable
fun PorscheHeader() {
    Text(
        text = "Ivan's Porsche Dream",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
}

// Display a single Porsche model item
@Composable
fun PorscheItem(model: PorscheModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = model.imageResId),
                contentDescription = model.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = model.name,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    FavoriteButton(model = model)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = model.description,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Toggle button for favorite status
@Composable
fun FavoriteButton(model: PorscheModel) {
    var isFavorite by remember { mutableStateOf(model.isFavorite) }

    IconButton(
        onClick = { isFavorite = !isFavorite },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = if (isFavorite) painterResource(id = R.drawable.porsche_911) else painterResource(id = R.drawable.porsche_macan),
            contentDescription = "Favorite Icon",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}

// Search bar for filtering models by name
@Composable
fun SearchBar(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Search Porsche Models") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

// Preview of the PorscheList
@Preview
@Composable
fun AppContent() {
    val porscheModels = getPorscheModels()
    PorscheList(porscheModels = porscheModels)
}

// List of Porsche models
fun getPorscheModels(): List<PorscheModel> {
    return listOf(
        PorscheModel(
            "911",
            R.drawable.porsche_911,
            "The Porsche 911 is a classic sports car known for its timeless design and performance."
        ),
        PorscheModel(
            "Cayenne",
            R.drawable.porsche_cayenne,
            "The Porsche Cayenne is a luxury SUV with a sporty character and powerful engines."
        ),
        PorscheModel(
            "Panamera",
            R.drawable.porsche_panamera,
            "The Porsche Panamera is a high-performance luxury sedan with four doors and seating for four."
        ),
        PorscheModel(
            "Boxster",
            R.drawable.porsche_boxster,
            "The Porsche Boxster is a two-seater convertible sports car."
        ),
        PorscheModel(
            "Macan",
            R.drawable.porsche_macan,
            "The Porsche Macan is a compact luxury SUV with sporty performance."
        )
    )
}

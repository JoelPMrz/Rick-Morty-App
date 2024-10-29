package com.example.paging3.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.paging3.R
import com.example.paging3.presentation.model.CharacterModel

@Composable
fun RickListScreen (rickListViewModel: RickListViewModel = hiltViewModel()){

    val characters = rickListViewModel.characters.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(
            id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    when {
        characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0 ->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = Color.White
                )
            }
        }

        characters.loadState.refresh is LoadState.NotLoading && characters.itemCount == 0 ->{
            Text(text = "No existen personajes")
        }

        characters.loadState.hasError -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red), contentAlignment = Alignment.Center){
                Text(text = "Ha sucedido un error, compruebe su conexión a internet")
            }
        }

        else -> {
            CharactersList(characters)

            if(characters.loadState.append is LoadState.Loading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun CharactersList(characters: LazyPagingItems<CharacterModel>) {
    LazyColumn {
        items(characters.itemCount){
            characters[it]?.let{ characterModel ->
                ItemList(characterModel)
            }
        }
    }
}

@Composable
fun ItemList(characterModel: CharacterModel) {

    var isExpanded by remember { mutableStateOf(false) }
    val imageHeight by animateDpAsState(targetValue = if (isExpanded) 500.dp else 300.dp)

    Box(modifier = Modifier
        .padding(24.dp)
        .clip(RoundedCornerShape(18))
        .border(2.dp, Color.Black, shape = RoundedCornerShape(0, 18, 0, 18))
        .fillMaxWidth()
        .height(imageHeight),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = characterModel.image,
            contentDescription = "character image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    isExpanded = !isExpanded
                },
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black.copy(alpha = 0f),
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.4f),
                        Color.Black.copy(alpha = 0.6f),
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black.copy(alpha = 1f),
                    )
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = characterModel.name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic
            )
        }
    }
}


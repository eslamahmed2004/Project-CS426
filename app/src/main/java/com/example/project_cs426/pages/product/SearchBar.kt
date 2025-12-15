package com.example.project_cs426.pages.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.project_cs426.ui.theme.Black
import com.example.project_cs426.ui.theme.WhiteGray
import com.example.project_cs426.ui.theme.MatteGray
import com.example.project_cs426.ui.theme.PrimaryGreen
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.input.ImeAction
import com.example.project_cs426.ui.theme.DarkGray

@Composable
fun SearchBar(modifier: Modifier = Modifier, placeHolder: String = "Search..", query: String = "", onSearch: (String) -> (Unit) = {}){
    var text by remember { mutableStateOf(query) }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
//            onSearch(newText)
        },
        placeholder = { Text(placeHolder, color= MatteGray, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)},
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Black
            )
        },
        trailingIcon = {
            if(text.isNotEmpty()){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear Text",
                    modifier = Modifier.clickable{
                        text = ""
                        onSearch("")
                    },
                    tint = MatteGray
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = WhiteGray,
            unfocusedContainerColor = WhiteGray,
            focusedTextColor = Black,
            unfocusedTextColor = Black,
            cursorColor = PrimaryGreen,
            focusedLeadingIconColor = Black,
            unfocusedLeadingIconColor = Black,
            focusedPlaceholderColor = MatteGray,
            unfocusedPlaceholderColor = MatteGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(text) }
        ),

    )
}
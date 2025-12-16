package com.l2round_voxvalleycom.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.l2round_voxvalleycom.R

@Composable
private fun PhoneNumberField() {

    var phone by remember { mutableStateOf("") }

    OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        placeholder = { Text("9876546789") },
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.india),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

package org.meteora.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.util.ifNotNull

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onClickWhenReadOnly: (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions(imeAction = ImeAction.Search)
    },
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholder: String = "Search",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MeteoraColor.Black50,
                        shape = shape
                    )
                    .clip(shape = shape)
                    .ifNotNull(onClickWhenReadOnly) {
                        Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onClick = it
                        )
                    }
                    .padding(all = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(modifier = Modifier.width(width = 6.dp))
                }
                Box(modifier = Modifier.weight(weight = 1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyLarge.copy(color = MeteoraColor.White50)
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) {
                    Spacer(modifier = Modifier.width(width = 6.dp))
                    trailingIcon()
                }
            }
        }
    )
}
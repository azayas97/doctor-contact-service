package io.zayasanton.app.types

data class DCSButton(
    val type: DCSButtonType = DCSButtonType.PRIMARY,
    val disabled: Boolean = false,
    val message: String
)

data class DCSField(
    val id: String,
    val label: String,
    val placeholder: String? = null,
    val type: DCSFieldType,
    val value: String? = null,
    val disabled: Boolean = false
)

data class DCSCard(
    val id: String,
    val name: String,
    val workplace: String,
    val phoneNumber: String,
    val phoneNumberLabel: String
)

data class DCSLink(
    val text: String,
    val url: String
)

data class DCSResultCard(
    val type: DCSResultCardType,
    val text: String
)

data class DCSPopup(
    val header: String,
    val body: String,
    val primary: DCSButton,
    val secondary: DCSButton
)

enum class DCSResultCardType {
    SUCCESS,
    ERROR
}

enum class DCSFieldType {
    TEXT,
    PASSWORD
}

enum class DCSButtonType {
    PRIMARY,
    SECONDARY
}

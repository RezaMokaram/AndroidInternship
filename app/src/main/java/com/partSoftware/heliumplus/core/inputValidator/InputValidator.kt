package com.partSoftware.heliumplus.core.inputValidator

import javax.inject.Inject

class InputValidator @Inject constructor() {

    fun validatePublishArticleInputs(title: String, content: String, tagId: String): String {
        return when {
            !isInTitleLength(title) -> ValidationMessages.BAD_LENGTH_TITLE
            !isArticleTitleValid(title) -> ValidationMessages.WRONG_TITLE

            !isArticleContentLengthValid(content) -> ValidationMessages.BAD_LENGTH_CONTENT

            tagId.isBlank() -> ValidationMessages.EMPTY_TAG

            else -> ValidationMessages.SUCCESSFULLY
        }
    }

    fun validateLoginInputs(phoneNumber: String, password: String): String {
        return when {
            phoneNumber.isBlank() -> ValidationMessages.EMPTY_PHONE_NUMBER
            !isPhoneNumberValid(phoneNumber) -> ValidationMessages.WRONG_PHONE_NUMBER

            password.isBlank() -> ValidationMessages.EMPTY_PASSWORD
            !isInMinimumSixCharRange(password) -> ValidationMessages.BAD_LENGTH_PASSWORD

            else -> ValidationMessages.SUCCESSFULLY
        }
    }

    fun validateRegisterInputs(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phoneNumber: String,
        password: String,
    ): String {
        return when {
            firstName.isBlank() -> ValidationMessages.EMPTY_FIRSTNAME
            !isInTextRange(firstName) -> ValidationMessages.BAD_LENGTH_FIRSTNAME
            !isPersian(firstName) -> ValidationMessages.WRONG_FIRSTNAME

            lastName.isBlank() -> ValidationMessages.EMPTY_LASTNAME
            !isInTextRange(lastName) -> ValidationMessages.BAD_LENGTH_LASTNAME
            !isPersian(lastName) -> ValidationMessages.WRONG_LASTNAME

            username.isBlank() -> ValidationMessages.EMPTY_USERNAME
            !isInMinimumSixCharRange(username) -> ValidationMessages.BAD_LENGTH_USERNAME
            !isUsernameValid(username) -> ValidationMessages.WRONG_USERNAME

            email.isBlank() -> ValidationMessages.EMPTY_EMAIL
            !isInTextRange(email) -> ValidationMessages.BAD_LENGTH_EMAIL
            !isEmailValid(email) -> ValidationMessages.WRONG_EMAIL

            phoneNumber.isBlank() -> ValidationMessages.EMPTY_PHONE_NUMBER
            !isPhoneNumberValid(phoneNumber) -> ValidationMessages.WRONG_PHONE_NUMBER

            password.isBlank() -> ValidationMessages.EMPTY_PASSWORD
            !isInMinimumSixCharRange(password) -> ValidationMessages.BAD_LENGTH_PASSWORD

            else -> ValidationMessages.SUCCESSFULLY
        }
    }
}

private fun isInTextRange(text: String): Boolean {
    return text.length in 1..255
}

private fun isInTitleLength(text: String): Boolean {
    return text.length in 5..250
}

private fun isInMinimumSixCharRange(text: String): Boolean {
    return text.length in 6..255
}

private fun isPersian(text: String): Boolean {
    val persianCharactersPattern = "[ءآأؤإئابپتثجچحخدذرزژسشصضطظعغفقکگلمنوهھی ]+"
    return persianCharactersPattern.toRegex().matches(text)
}

private fun isUsernameValid(username: String): Boolean {
    if (username.first() == '_' || username.last() == '_') return false
    val usernamePattern = "[a-zA-Z0-9_]+"
    return usernamePattern.toRegex().matches(username)
}

private fun isEmailValid(email: String): Boolean {
    val emailPattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    return emailPattern.toRegex().matches(email)
}

private fun isPhoneNumberValid(phoneNumber: String): Boolean {
    val phonePattern = "09[0-9]{9}"
    return phonePattern.toRegex().matches(phoneNumber)
}

private fun isArticleTitleValid(title: String): Boolean {
    val articleTitlePattern =
        "[\u0621-\u0628\u062A-\u063A\u0641-\u0642\u0644-\u0648\u064E-\u0651\u0655\u067E\u0686\u0698\u06A9\u06AF\u06BE\u06CC a-zA-Z0-9]+"
    return articleTitlePattern.toRegex().matches(title)
}

private fun isArticleContentLengthValid(content: String): Boolean {
    return content.length >= 100
    // server just accept the strings with over 99 characters length
}
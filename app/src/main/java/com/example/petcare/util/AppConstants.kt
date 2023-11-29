package com.example.petcare.util

class AppConstants {
    companion object {

        //Shared Preferences
        const val APP_SHARED_PREFERENCES = "AppSharedPreferences"


        //Parameter Passage
        const val POST_ID_TO_CONFIRMATION = "PostIdToConfirmation"
        const val POST_VALUE_TO_CONFIRMATION = "PostValueToConfirmation"


        //Post type
        const val POST_TYPE_FEED = 0
        const val POST_TYPE_DONATION = 1

        enum class RegisterErrors {
            ALREADY_EXIST, SUCCESS, UNKNOWN_FAILURE
        }

        enum class UserType {
            COMMON, ONG
        }
    }
}
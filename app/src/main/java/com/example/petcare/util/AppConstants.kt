package com.example.petcare.util

class AppConstants {
    companion object {

        //Log
        const val LOG_DATABASE = "PetCare-Database"

        //Database
        const val DATABASE_NAME = "PetCare"
        const val USER_TABLE = "user"
        const val POST_TABLE = "post"

        //Shared Preferences
        const val APP_SHARED_PREFERENCES = "AppSharedPreferences"
        const val LOGIN_SHARED_PREFERENCES = "LoginSharedPreferences"

        //Parameter Passage
        const val POST_ID_TO_CONFIRMATION = "PostIdToConfirmation"

        //Post type
        const val POST_TYPE_FEED = 1
        const val POST_TYPE_DONATION = 2

        enum class RegisterErrors {
            ALREADY_EXIST, SUCCESS, UNKNOWN_FAILURE
        }

        enum class UserType {
            COMMON, ONG
        }

        enum class ANIMAL_TYPE {
            DOG, CAT, BIRD
        }
    }
}
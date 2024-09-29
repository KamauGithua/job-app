package com.kamau.ist.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kamau.ist.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavControllerModule {

//    @Provides
//    @ActivityScoped
//    fun provideNavController(activity: Activity): NavController {
//        return Navigation.findNavController(activity, com.google.firebase.database.collection.R.id.end)
//    }
}

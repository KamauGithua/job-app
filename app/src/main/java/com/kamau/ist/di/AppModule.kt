
// AppModule.kt
package com.kamau.ist.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kamau.ist.repository.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Uncomment this if you need to provide the FirestoreRepository as well
    @Provides
    @Singleton
    fun provideFirestoreRepository(
        firestore: FirebaseFirestore
    ): FirestoreRepository {
        return FirestoreRepository(firestore)
    }
}


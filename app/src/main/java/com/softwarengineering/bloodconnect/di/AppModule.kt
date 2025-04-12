package com.softwarengineering.bloodconnect.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.softwarengineering.bloodconnect.data.datasource.DonorDatasource
import com.softwarengineering.bloodconnect.data.repo.DonorRepostory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providedonordatasource(collection:CollectionReference):DonorDatasource{
        return DonorDatasource(collection)
    }
    @Provides
    @Singleton
    fun providedonorepostory(donorDatasource: DonorDatasource):DonorRepostory{
        return DonorRepostory(donorDatasource)
    }
    @Provides
    @Singleton
    fun providecollectionDonor():CollectionReference{
        return Firebase.firestore.collection("donor")
    }


}

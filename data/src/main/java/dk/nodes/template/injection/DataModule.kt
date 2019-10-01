package dk.nodes.template.injection

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("data", Context.MODE_PRIVATE)
    }

}
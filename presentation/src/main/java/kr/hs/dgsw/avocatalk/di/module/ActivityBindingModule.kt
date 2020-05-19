package kr.hs.dgsw.avocatalk.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.hs.dgsw.avocatalk.di.scope.PerActivity
import kr.hs.dgsw.avocatalk.view.activity.LoginActivity
import kr.hs.dgsw.avocatalk.view.activity.MainActivity
import kr.hs.dgsw.avocatalk.view.activity.RegisterActivity
import kr.hs.dgsw.avocatalk.view.activity.SplashActivity


@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindingLoginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindingMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindingRegisterActivity(): RegisterActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindingSplashActivity(): SplashActivity
}
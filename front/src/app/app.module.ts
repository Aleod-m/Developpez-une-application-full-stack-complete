import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { ComponentLibModule } from './components/module';
import { MaterialModules } from './material.modules';
import { AppRoutingModule } from './app-routing.module';

import { JwtInterceptor } from './interceptors/jwt.interceptor';

import { AppComponent } from './app.component';
import { LandingPage } from './pages/landing/landing.page';
import { LoginPage } from './pages/auth/login.page';
import { SigninPage } from './pages/auth/signin.page';
import { ProfilePage } from './pages/profile/profile.page';
import { FeedPage } from './pages/articles/feed.page';
import { TopicsPage } from './pages/topics/topics.page';
import { ArticleReadPage } from './pages/articles/article-read.page';
import { ArticleNewPage } from './pages/articles/article-new.page';


@NgModule({
  declarations: [
    AppComponent,
    LandingPage,
    LoginPage,
    SigninPage,
    ProfilePage,
    TopicsPage,
    FeedPage,
    ArticleReadPage,
    ArticleNewPage,
  ],
  imports: [
    MaterialModules,
    ComponentLibModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }

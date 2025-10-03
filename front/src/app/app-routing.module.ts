import { NgModule, Injectable } from '@angular/core';
import { RouterModule, Routes ,CanActivate, Router } from "@angular/router";

import { LandingPage } from './pages/landing/landing.page';
import { LoginPage } from './pages/auth/login.page';
import { SigninPage } from './pages/auth/signin.page';
import { ProfilePage } from './pages/profile/profile.page';
import { TopicsPage } from './pages/topics/topics.page';
import { FeedPage } from './pages/articles/feed.page';
import { ArticleReadPage } from './pages/articles/article-read.page';
import { ArticleNewPage } from './pages/articles/article-new.page';
import { AuthService } from './services/auth.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLogged()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
@Injectable({
  providedIn: 'root'
})
export class UnAuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isLogged()) {
      this.router.navigate(['/feed']);
      return false;
    } else {
      return true;
    }
  }
}


const routes: Routes = [
  { canActivate: [UnAuthGuard], path: '', component: LandingPage },
  { canActivate: [UnAuthGuard], path: 'login', component: LoginPage },
  { canActivate: [UnAuthGuard], path: 'signin', component: SigninPage },
  { canActivate: [AuthGuard], path: 'profile', component: ProfilePage },
  { canActivate: [AuthGuard], path: 'feed', component: FeedPage },
  { canActivate: [AuthGuard], path: 'articles/create', component: ArticleNewPage },
  { canActivate: [AuthGuard], path: 'articles/:id', component: ArticleReadPage },
  { canActivate: [AuthGuard], path: 'topics', component: TopicsPage },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }

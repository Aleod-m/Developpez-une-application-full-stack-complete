import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'page-container',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss'],
})
export class PageContainerComponent {
  showSidebar = false;

  constructor(
    private router: Router,
    private authSrvc: AuthService,
  ) {}

  public logout(): void {
    this.authSrvc.logout();
    this.router.navigate(['']);
  }
}

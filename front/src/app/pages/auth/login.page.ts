import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginInformation } from 'src/app/interfaces/auth.interfaces';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class LoginPage {
  hide = true;
  onError = false;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
      ]
    ],
    password: [
      '',
      [
        Validators.required,
      ]
    ]
  });

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
  ) { }

  public submit(): void {
    const loginRequest = this.form.value as LoginInformation;
    this.authService.login(loginRequest).subscribe({
      next: (_) => this.router.navigate(['/feed']),
      error: _ => this.onError = true,
    });
  }

}

import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignUpInformation } from 'src/app/interfaces/auth.interfaces';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class SigninPage {
  hide = true;
  onError = false;


  public form = this.fb.group({
    user_name: [
      '',
      [Validators.required]
    ],
    email: [
      "",
      [Validators.email, Validators.required]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-])/)
      ]
    ]
  });

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
  ) { }

  emailIsInvalid(): boolean {
    return this.form.controls.email.invalid
  }

  passwordIsInvalid(): boolean {
    return this.form.controls.password.invalid
  }

  getEmailErrorMessage(): string {
    if (this.form.controls.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.form.controls.email.hasError('email') ? 'Not a valid email' : '';
  }

  public submit(): void {
    const signupRequest = this.form.value as SignUpInformation;
    this.authService.register(signupRequest).subscribe({
      next: (_) => {
        this.router.navigate(['/login']);
      },
      error: _error => this.onError = true,
    });
  }

}

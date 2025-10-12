import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { Observable, tap } from 'rxjs';
import { User, UserUpdate } from 'src/app/interfaces/user.interfaces';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { TopicSubscription, SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  constructor(
    private fb: FormBuilder,
    private usersSrvc: UserService,
    private authSrvc: AuthService,
    private subscriptionSrvc: SubscriptionService,
  ) { }

  subscriptions$!: Observable<TopicSubscription[]>;

  hide = true;
  form = this.fb.group({
    username: [
      '',
      [Validators.required]
    ],
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-])/)
      ]
    ],
    confirmPassword: [
      '',
      [
        Validators.required,
        this.validateSamePassword
      ]
    ]
  });

  private validateSamePassword(control: AbstractControl): ValidationErrors | null {
    const password = control.parent?.get('password');
    const confirmPassword = control.parent?.get('confirmPassword');
    return password?.value == confirmPassword?.value ? null : { 'notSame': true };
  }


  submit() {
    if (this.form.valid) {
      const currentUser = this.authSrvc.user();
      const fval = this.form.getRawValue();
      const user: UserUpdate = {
        username: fval.username || undefined,
        email: fval.email || undefined,
        subscription_ids: currentUser?.subscription_ids || [],
        password: fval.password || undefined,
      };
      this.usersSrvc.update(currentUser!.id, user).subscribe();
    }
  }

  getEmailError() {
    const control = this.form.controls.email;
    if (control.hasError('required')) {
      return 'Email requis.';
    } else if (control.hasError('email')) {
      return 'Email invalide.';
    } else {
      return '';
    }
  }

  getPasswordError() {
    const control = this.form.controls.password;
    if (control.hasError('required')) {
      return "Le mot de passe est requis.";
    } else if (control.hasError('minlength')) {
      return "Le mot de passe doit faire au moins 8 characteres.";
    } else if (control.hasError('pattern')) {
      return " Le mot de passe doit contenir une majuscule, un chiffre et un charactere speciale.";
    } else {
      return "";
    }
  }

  getConfirmPasswordError() {
    const control = this.form.controls.confirmPassword;
    if (control.hasError('required')) {
      return "La confirmation du mot de passe est requise.";
    } else if (control.hasError('notSame')) {
      return "Le mot de passe ne correspond pas."
    } else {
      return "";
    }
  }

  ngOnInit() {
    this.authSrvc.me().pipe(
      tap((user: User) => {
        this.form.setValue({
          username: user.username || "",
          email: user.email || "",
          password: '',
          confirmPassword: '',
        })
      }),
    ).subscribe();
    this.subscriptionSrvc.getSubsciptionForUser();
    this.subscriptions$ = this.subscriptionSrvc.subscriptions$;
  }

  onUnSubscribe(topicId: number): void {
    this.subscriptionSrvc.updateTopicSubscription(topicId, false);
  }
}

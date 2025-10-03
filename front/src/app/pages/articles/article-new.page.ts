import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewArticle } from 'src/app/interfaces/article.interfaces';
import { ArticleService } from 'src/app/services/article.service';
import { AuthService } from 'src/app/services/auth.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-article-new',
  templateUrl: './article-new.page.html',
  styleUrls: ['./article-new.page.scss'],
})
export class ArticleNewPage implements OnInit {
  public topics = this.topicsSrvc.getAll();

  public form = this.fb.group({
    title: ['', [Validators.required]],
    topic_id: ['', [Validators.required]],
    content: ['', [Validators.required]],
  });

  constructor(
    private articlesSrvc: ArticleService,
    private topicsSrvc: TopicService,
    private authSrvc: AuthService,
    private fb: FormBuilder,
    private router: Router,
  ) {

  }

  ngOnInit(): void {}

  submit(): void {
    if (this.form.valid) {
      this.authSrvc.me().subscribe({
        next: (user) => {
          if (!user)
            return;
          const fval = this.form.getRawValue();
          var article: NewArticle = {
            author_id: user.id,
            title: fval.title!,
            topic_id: Number(fval.topic_id!),
            content: fval.content!,
          };
          this.articlesSrvc.createArticle(article).subscribe({
            next: (art) => this.router.navigate([`/articles/${art.id}`])
          });
        }
      })
    }
  }

}

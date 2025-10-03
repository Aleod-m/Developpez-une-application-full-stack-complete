import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Summary } from '../interfaces/article.interfaces';

@Component({
  selector: 'article-card[article]',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss'],
})
export class ArticleCardComponent {
  @Input()
  article!: Summary;

  constructor(private router: Router) {}

  navigate() {
    this.router.navigate([`/articles/${this.article.id}`]);

  }
}

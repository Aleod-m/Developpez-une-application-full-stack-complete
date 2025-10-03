import { Component, OnInit } from '@angular/core';
import { Summary } from 'src/app/interfaces/article.interfaces';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './feed.page.html',
  styleUrls: ['./feed.page.scss'],
})
export class FeedPage implements OnInit {

  articles!: Summary[];

  sortAsc: boolean = false;

  constructor(
    private articleSrvc: ArticleService,
  ) {
  }

  ngOnInit(): void {
    this.articleSrvc.getArticleSummaries().subscribe({
      next: (articles) => {
        this.articles = articles;
        this.sort()
      },
    });
  }

  sort() {
    this.articles = [...this.articles.sort((a, b) =>
        this.sortAsc
          ? new Date(a.created_at).getTime() - new Date(b.created_at).getTime()
          : new Date(b.created_at).getTime() - new Date(a.created_at).getTime())];
  }

  toggleSort() {
    this.sortAsc = !this.sortAsc;
    this.sort();
  }

}

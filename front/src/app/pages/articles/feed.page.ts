import { Component, OnInit } from '@angular/core';
import { Observable, map, filter } from 'rxjs';
import { Summary } from 'src/app/interfaces/article.interfaces';
import { Topic } from 'src/app/interfaces/topic.interfaces';
import { ArticleService } from 'src/app/services/article.service';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-articles',
  templateUrl: './feed.page.html',
  styleUrls: ['./feed.page.scss'],
})
export class FeedPage implements OnInit {

  articles!: Summary[];

  sortAsc: boolean = false;

  subscription_ids$: Observable<number[]> = this.subscriptionSrvc.subscriptions$.pipe(
    filter((subs) => subs.length > 0),
    map((subs) =>
        subs.filter((sub) => sub.subscribed)
          .map((sub) => sub.topic.id)
    )
  );

  constructor(
    private articleSrvc: ArticleService,
    private subscriptionSrvc: SubscriptionService,
  ) {
  }

  ngOnInit(): void {
    this.subscriptionSrvc.getAll();
    this.articleSrvc.getArticleSummaries().subscribe({
      next: (articles) => {
        this.articles = articles;
        this.filter()
        this.sort()
      },
    });
  }

  filter() {
    this.subscription_ids$.subscribe({
      next: (sub_ids) => {
        console.log(sub_ids);
        console.log(this.articles);
        this.articles = this.articles.filter((article) => sub_ids.includes(article.topic_id));
        console.log(this.articles);
      }
    }).unsubscribe()
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

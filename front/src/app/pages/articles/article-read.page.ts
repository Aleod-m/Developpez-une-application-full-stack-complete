import { Component, OnInit, ViewChild } from '@angular/core';
import { CommentCreation, Comment } from "src/app/interfaces/comment.interfaces";
import { FormBuilder, FormGroupDirective, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/interfaces/article.interfaces';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';
import { Observable, map, BehaviorSubject, filter } from 'rxjs';

@Component({
  selector: 'app-article-read',
  templateUrl: './article-read.page.html',
  styleUrls: ['./article-read.page.scss'],
})
export class ArticleReadPage implements OnInit {
  id!: number;

  article_data: BehaviorSubject<Article | undefined> = new BehaviorSubject<Article | undefined>(undefined);
  article$: Observable<Article> = this.article_data.asObservable()
    .pipe(filter((art): art is Article => !!art));

  comments_data: BehaviorSubject<Comment[] | undefined> = new BehaviorSubject<Comment[] | undefined>(undefined);
  comments$: Observable<Comment[]> = this.comments_data.asObservable()
    .pipe(filter((comments): comments is Comment[] => !!comments))

  topic_label_data: BehaviorSubject<string | undefined> = new BehaviorSubject<string | undefined>(undefined);
  topic_label$: Observable<string> = this.topic_label_data.asObservable()
    .pipe(filter((topic_label): topic_label is string => !!topic_label));

  author_name_data: BehaviorSubject<string | undefined> = new BehaviorSubject<string | undefined>(undefined);
  author_name$: Observable<string> = this.author_name_data.asObservable()
    .pipe(filter((author_name): author_name is string => !!author_name));

  @ViewChild(FormGroupDirective) formDirective: FormGroupDirective | undefined;
  public form = this.formBuilder.group({
    comment: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255)]],
  });
  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private articleService: ArticleService,
    private topicService: TopicService,
    private userService: UserService,
    private commentService: CommentService,
  ) {

  }
  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadArticle()
    this.loadComments()
  }

  loadArticle() {
    this.articleService.getArticle(this.id).subscribe((article) => {
      this.article_data.next(article)
      this.topicService.get(article.topic_id).pipe(map(topic => topic.label)).subscribe(label => this.topic_label_data.next(label));
      this.userService.get(article.author_id).pipe(map(user => user.username)).subscribe((username) => this.author_name_data.next(username));
    });
  }

  loadComments() {
    this.commentService.getCommentsForAtricle(this.id).subscribe((comments) => {
      this.comments_data.next(comments);
    });
  }

  commentIsInvalid(): boolean {
    return this.form.controls.comment.invalid;
  }

  getCommentErrors(): string {
    if (this.form.controls.comment.hasError('required')) {
      return 'Entrez un commentaire';
    }
    else if (this.form.controls.comment.hasError('maxlength')) {
      console.log(this.form.controls.comment.errors)
      return 'Commentaire trop long';
    }

    return '';
  }

  submit() {
    if (this.form.valid) {
      const comment = this.form.value.comment!;
      const createComment: CommentCreation = {
        content: comment,
        post_id: this.article_data.value!.id,
      }

      this.commentService.create(createComment).subscribe(
        {
          next: () => {
            this.formDirective?.resetForm();
            this.form.reset();
            this.loadComments();
          },
        });
    }
  }

}

import { NgModule } from '@angular/core';
import { PageContainerComponent } from './page.component';
import { ArticleCardComponent } from './article-card.component';
import { ArticleCommentComponent } from './article-comment.component';
import { MaterialModules } from '../material.modules';
import { TopicCardComponent } from './topic-card.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

const components = [
  PageContainerComponent,
  ArticleCardComponent,
  ArticleCommentComponent,
  TopicCardComponent,
];

@NgModule({
  declarations: components,
  imports: [
    MaterialModules,
    RouterModule,
    CommonModule,
  ],
  exports: components,
})
export class ComponentLibModule { }


import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISociety } from '../society.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-society-detail',
  templateUrl: './society-detail.component.html',
})
export class SocietyDetailComponent implements OnInit {
  society: ISociety | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ society }) => {
      this.society = society;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}

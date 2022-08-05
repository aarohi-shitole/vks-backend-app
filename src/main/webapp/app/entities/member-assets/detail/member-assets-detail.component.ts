import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberAssets } from '../member-assets.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-member-assets-detail',
  templateUrl: './member-assets-detail.component.html',
})
export class MemberAssetsDetailComponent implements OnInit {
  memberAssets: IMemberAssets | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberAssets }) => {
      this.memberAssets = memberAssets;
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

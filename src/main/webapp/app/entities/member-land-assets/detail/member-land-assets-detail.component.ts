import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMemberLandAssets } from '../member-land-assets.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-member-land-assets-detail',
  templateUrl: './member-land-assets-detail.component.html',
})
export class MemberLandAssetsDetailComponent implements OnInit {
  memberLandAssets: IMemberLandAssets | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLandAssets }) => {
      this.memberLandAssets = memberLandAssets;
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

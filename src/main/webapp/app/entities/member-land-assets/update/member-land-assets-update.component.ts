import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MemberLandAssetsFormService, MemberLandAssetsFormGroup } from './member-land-assets-form.service';
import { IMemberLandAssets } from '../member-land-assets.model';
import { MemberLandAssetsService } from '../service/member-land-assets.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-member-land-assets-update',
  templateUrl: './member-land-assets-update.component.html',
})
export class MemberLandAssetsUpdateComponent implements OnInit {
  isSaving = false;
  memberLandAssets: IMemberLandAssets | null = null;

  editForm: MemberLandAssetsFormGroup = this.memberLandAssetsFormService.createMemberLandAssetsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected memberLandAssetsService: MemberLandAssetsService,
    protected memberLandAssetsFormService: MemberLandAssetsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLandAssets }) => {
      this.memberLandAssets = memberLandAssets;
      if (memberLandAssets) {
        this.updateForm(memberLandAssets);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('vksBackendApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberLandAssets = this.memberLandAssetsFormService.getMemberLandAssets(this.editForm);
    if (memberLandAssets.id !== null) {
      this.subscribeToSaveResponse(this.memberLandAssetsService.update(memberLandAssets));
    } else {
      this.subscribeToSaveResponse(this.memberLandAssetsService.create(memberLandAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberLandAssets>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(memberLandAssets: IMemberLandAssets): void {
    this.memberLandAssets = memberLandAssets;
    this.memberLandAssetsFormService.resetForm(this.editForm, memberLandAssets);
  }
}

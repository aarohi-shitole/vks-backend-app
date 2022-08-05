import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberAssetsFormService, MemberAssetsFormGroup } from './member-assets-form.service';
import { IMemberAssets } from '../member-assets.model';
import { MemberAssetsService } from '../service/member-assets.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { AssetType } from 'app/entities/enumerations/asset-type.model';

@Component({
  selector: 'jhi-member-assets-update',
  templateUrl: './member-assets-update.component.html',
})
export class MemberAssetsUpdateComponent implements OnInit {
  isSaving = false;
  memberAssets: IMemberAssets | null = null;
  assetTypeValues = Object.keys(AssetType);

  membersSharedCollection: IMember[] = [];

  editForm: MemberAssetsFormGroup = this.memberAssetsFormService.createMemberAssetsFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected memberAssetsService: MemberAssetsService,
    protected memberAssetsFormService: MemberAssetsFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberAssets }) => {
      this.memberAssets = memberAssets;
      if (memberAssets) {
        this.updateForm(memberAssets);
      }

      this.loadRelationshipsOptions();
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
    const memberAssets = this.memberAssetsFormService.getMemberAssets(this.editForm);
    if (memberAssets.id !== null) {
      this.subscribeToSaveResponse(this.memberAssetsService.update(memberAssets));
    } else {
      this.subscribeToSaveResponse(this.memberAssetsService.create(memberAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberAssets>>): void {
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

  protected updateForm(memberAssets: IMemberAssets): void {
    this.memberAssets = memberAssets;
    this.memberAssetsFormService.resetForm(this.editForm, memberAssets);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      memberAssets.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.memberAssets?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NomineeFormService, NomineeFormGroup } from './nominee-form.service';
import { INominee } from '../nominee.model';
import { NomineeService } from '../service/nominee.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

@Component({
  selector: 'jhi-nominee-update',
  templateUrl: './nominee-update.component.html',
})
export class NomineeUpdateComponent implements OnInit {
  isSaving = false;
  nominee: INominee | null = null;

  membersSharedCollection: IMember[] = [];

  editForm: NomineeFormGroup = this.nomineeFormService.createNomineeFormGroup();

  constructor(
    protected nomineeService: NomineeService,
    protected nomineeFormService: NomineeFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nominee }) => {
      this.nominee = nominee;
      if (nominee) {
        this.updateForm(nominee);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nominee = this.nomineeFormService.getNominee(this.editForm);
    if (nominee.id !== null) {
      this.subscribeToSaveResponse(this.nomineeService.update(nominee));
    } else {
      this.subscribeToSaveResponse(this.nomineeService.create(nominee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INominee>>): void {
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

  protected updateForm(nominee: INominee): void {
    this.nominee = nominee;
    this.nomineeFormService.resetForm(this.editForm, nominee);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(this.membersSharedCollection, nominee.member);
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.nominee?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}

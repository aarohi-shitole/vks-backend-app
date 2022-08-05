import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberFormService, MemberFormGroup } from './member-form.service';
import { IMember } from '../member.model';
import { MemberService } from '../service/member.service';
import { IMemberBank } from 'app/entities/member-bank/member-bank.model';
import { MemberBankService } from 'app/entities/member-bank/service/member-bank.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

@Component({
  selector: 'jhi-member-update',
  templateUrl: './member-update.component.html',
})
export class MemberUpdateComponent implements OnInit {
  isSaving = false;
  member: IMember | null = null;
  genderValues = Object.keys(Gender);
  statusValues = Object.keys(Status);
  loanStatusValues = Object.keys(LoanStatus);

  memberBanksCollection: IMemberBank[] = [];
  societiesSharedCollection: ISociety[] = [];

  editForm: MemberFormGroup = this.memberFormService.createMemberFormGroup();

  constructor(
    protected memberService: MemberService,
    protected memberFormService: MemberFormService,
    protected memberBankService: MemberBankService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMemberBank = (o1: IMemberBank | null, o2: IMemberBank | null): boolean => this.memberBankService.compareMemberBank(o1, o2);

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ member }) => {
      this.member = member;
      if (member) {
        this.updateForm(member);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const member = this.memberFormService.getMember(this.editForm);
    if (member.id !== null) {
      this.subscribeToSaveResponse(this.memberService.update(member));
    } else {
      this.subscribeToSaveResponse(this.memberService.create(member));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMember>>): void {
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

  protected updateForm(member: IMember): void {
    this.member = member;
    this.memberFormService.resetForm(this.editForm, member);

    this.memberBanksCollection = this.memberBankService.addMemberBankToCollectionIfMissing<IMemberBank>(
      this.memberBanksCollection,
      member.memberBank
    );
    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      member.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberBankService
      .query({ 'memberId.specified': 'false' })
      .pipe(map((res: HttpResponse<IMemberBank[]>) => res.body ?? []))
      .pipe(
        map((memberBanks: IMemberBank[]) =>
          this.memberBankService.addMemberBankToCollectionIfMissing<IMemberBank>(memberBanks, this.member?.memberBank)
        )
      )
      .subscribe((memberBanks: IMemberBank[]) => (this.memberBanksCollection = memberBanks));

    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(map((societies: ISociety[]) => this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.member?.society)))
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}

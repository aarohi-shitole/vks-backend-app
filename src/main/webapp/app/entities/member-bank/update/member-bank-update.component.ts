import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MemberBankFormService, MemberBankFormGroup } from './member-bank-form.service';
import { IMemberBank } from '../member-bank.model';
import { MemberBankService } from '../service/member-bank.service';

@Component({
  selector: 'jhi-member-bank-update',
  templateUrl: './member-bank-update.component.html',
})
export class MemberBankUpdateComponent implements OnInit {
  isSaving = false;
  memberBank: IMemberBank | null = null;

  editForm: MemberBankFormGroup = this.memberBankFormService.createMemberBankFormGroup();

  constructor(
    protected memberBankService: MemberBankService,
    protected memberBankFormService: MemberBankFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberBank }) => {
      this.memberBank = memberBank;
      if (memberBank) {
        this.updateForm(memberBank);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberBank = this.memberBankFormService.getMemberBank(this.editForm);
    if (memberBank.id !== null) {
      this.subscribeToSaveResponse(this.memberBankService.update(memberBank));
    } else {
      this.subscribeToSaveResponse(this.memberBankService.create(memberBank));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberBank>>): void {
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

  protected updateForm(memberBank: IMemberBank): void {
    this.memberBank = memberBank;
    this.memberBankFormService.resetForm(this.editForm, memberBank);
  }
}

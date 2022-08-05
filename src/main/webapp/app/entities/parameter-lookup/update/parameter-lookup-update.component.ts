import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ParameterLookupFormService, ParameterLookupFormGroup } from './parameter-lookup-form.service';
import { IParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { ParameterType } from 'app/entities/enumerations/parameter-type.model';

@Component({
  selector: 'jhi-parameter-lookup-update',
  templateUrl: './parameter-lookup-update.component.html',
})
export class ParameterLookupUpdateComponent implements OnInit {
  isSaving = false;
  parameterLookup: IParameterLookup | null = null;
  parameterTypeValues = Object.keys(ParameterType);

  editForm: ParameterLookupFormGroup = this.parameterLookupFormService.createParameterLookupFormGroup();

  constructor(
    protected parameterLookupService: ParameterLookupService,
    protected parameterLookupFormService: ParameterLookupFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      this.parameterLookup = parameterLookup;
      if (parameterLookup) {
        this.updateForm(parameterLookup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameterLookup = this.parameterLookupFormService.getParameterLookup(this.editForm);
    if (parameterLookup.id !== null) {
      this.subscribeToSaveResponse(this.parameterLookupService.update(parameterLookup));
    } else {
      this.subscribeToSaveResponse(this.parameterLookupService.create(parameterLookup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameterLookup>>): void {
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

  protected updateForm(parameterLookup: IParameterLookup): void {
    this.parameterLookup = parameterLookup;
    this.parameterLookupFormService.resetForm(this.editForm, parameterLookup);
  }
}

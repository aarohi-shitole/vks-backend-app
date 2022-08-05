import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INominee, NewNominee } from '../nominee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INominee for edit and NewNomineeFormGroupInput for create.
 */
type NomineeFormGroupInput = INominee | PartialWithRequiredKeyOf<NewNominee>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INominee | NewNominee> = Omit<T, 'nomineeDeclareDate' | 'lastModified' | 'createdOn'> & {
  nomineeDeclareDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type NomineeFormRawValue = FormValueOf<INominee>;

type NewNomineeFormRawValue = FormValueOf<NewNominee>;

type NomineeFormDefaults = Pick<NewNominee, 'id' | 'nomineeDeclareDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type NomineeFormGroupContent = {
  id: FormControl<NomineeFormRawValue['id'] | NewNominee['id']>;
  firstName: FormControl<NomineeFormRawValue['firstName']>;
  middleName: FormControl<NomineeFormRawValue['middleName']>;
  lastName: FormControl<NomineeFormRawValue['lastName']>;
  fatherName: FormControl<NomineeFormRawValue['fatherName']>;
  motherName: FormControl<NomineeFormRawValue['motherName']>;
  guardianName: FormControl<NomineeFormRawValue['guardianName']>;
  gender: FormControl<NomineeFormRawValue['gender']>;
  dob: FormControl<NomineeFormRawValue['dob']>;
  aadharCard: FormControl<NomineeFormRawValue['aadharCard']>;
  nomineeDeclareDate: FormControl<NomineeFormRawValue['nomineeDeclareDate']>;
  relation: FormControl<NomineeFormRawValue['relation']>;
  permanentAddr: FormControl<NomineeFormRawValue['permanentAddr']>;
  lastModified: FormControl<NomineeFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<NomineeFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<NomineeFormRawValue['createdBy']>;
  createdOn: FormControl<NomineeFormRawValue['createdOn']>;
  isDeleted: FormControl<NomineeFormRawValue['isDeleted']>;
  member: FormControl<NomineeFormRawValue['member']>;
};

export type NomineeFormGroup = FormGroup<NomineeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NomineeFormService {
  createNomineeFormGroup(nominee: NomineeFormGroupInput = { id: null }): NomineeFormGroup {
    const nomineeRawValue = this.convertNomineeToNomineeRawValue({
      ...this.getFormDefaults(),
      ...nominee,
    });
    return new FormGroup<NomineeFormGroupContent>({
      id: new FormControl(
        { value: nomineeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(nomineeRawValue.firstName),
      middleName: new FormControl(nomineeRawValue.middleName),
      lastName: new FormControl(nomineeRawValue.lastName),
      fatherName: new FormControl(nomineeRawValue.fatherName),
      motherName: new FormControl(nomineeRawValue.motherName),
      guardianName: new FormControl(nomineeRawValue.guardianName),
      gender: new FormControl(nomineeRawValue.gender),
      dob: new FormControl(nomineeRawValue.dob),
      aadharCard: new FormControl(nomineeRawValue.aadharCard),
      nomineeDeclareDate: new FormControl(nomineeRawValue.nomineeDeclareDate),
      relation: new FormControl(nomineeRawValue.relation),
      permanentAddr: new FormControl(nomineeRawValue.permanentAddr),
      lastModified: new FormControl(nomineeRawValue.lastModified),
      lastModifiedBy: new FormControl(nomineeRawValue.lastModifiedBy),
      createdBy: new FormControl(nomineeRawValue.createdBy),
      createdOn: new FormControl(nomineeRawValue.createdOn),
      isDeleted: new FormControl(nomineeRawValue.isDeleted),
      member: new FormControl(nomineeRawValue.member),
    });
  }

  getNominee(form: NomineeFormGroup): INominee | NewNominee {
    return this.convertNomineeRawValueToNominee(form.getRawValue() as NomineeFormRawValue | NewNomineeFormRawValue);
  }

  resetForm(form: NomineeFormGroup, nominee: NomineeFormGroupInput): void {
    const nomineeRawValue = this.convertNomineeToNomineeRawValue({ ...this.getFormDefaults(), ...nominee });
    form.reset(
      {
        ...nomineeRawValue,
        id: { value: nomineeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NomineeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      nomineeDeclareDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertNomineeRawValueToNominee(rawNominee: NomineeFormRawValue | NewNomineeFormRawValue): INominee | NewNominee {
    return {
      ...rawNominee,
      nomineeDeclareDate: dayjs(rawNominee.nomineeDeclareDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawNominee.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawNominee.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertNomineeToNomineeRawValue(
    nominee: INominee | (Partial<NewNominee> & NomineeFormDefaults)
  ): NomineeFormRawValue | PartialWithRequiredKeyOf<NewNomineeFormRawValue> {
    return {
      ...nominee,
      nomineeDeclareDate: nominee.nomineeDeclareDate ? nominee.nomineeDeclareDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: nominee.lastModified ? nominee.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: nominee.createdOn ? nominee.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

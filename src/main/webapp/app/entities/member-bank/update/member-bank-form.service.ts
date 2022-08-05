import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberBank, NewMemberBank } from '../member-bank.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberBank for edit and NewMemberBankFormGroupInput for create.
 */
type MemberBankFormGroupInput = IMemberBank | PartialWithRequiredKeyOf<NewMemberBank>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberBank | NewMemberBank> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberBankFormRawValue = FormValueOf<IMemberBank>;

type NewMemberBankFormRawValue = FormValueOf<NewMemberBank>;

type MemberBankFormDefaults = Pick<NewMemberBank, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type MemberBankFormGroupContent = {
  id: FormControl<MemberBankFormRawValue['id'] | NewMemberBank['id']>;
  bankName: FormControl<MemberBankFormRawValue['bankName']>;
  branchName: FormControl<MemberBankFormRawValue['branchName']>;
  accountNumber: FormControl<MemberBankFormRawValue['accountNumber']>;
  ifsccode: FormControl<MemberBankFormRawValue['ifsccode']>;
  lastModified: FormControl<MemberBankFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberBankFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberBankFormRawValue['createdBy']>;
  createdOn: FormControl<MemberBankFormRawValue['createdOn']>;
  isDeleted: FormControl<MemberBankFormRawValue['isDeleted']>;
};

export type MemberBankFormGroup = FormGroup<MemberBankFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberBankFormService {
  createMemberBankFormGroup(memberBank: MemberBankFormGroupInput = { id: null }): MemberBankFormGroup {
    const memberBankRawValue = this.convertMemberBankToMemberBankRawValue({
      ...this.getFormDefaults(),
      ...memberBank,
    });
    return new FormGroup<MemberBankFormGroupContent>({
      id: new FormControl(
        { value: memberBankRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      bankName: new FormControl(memberBankRawValue.bankName),
      branchName: new FormControl(memberBankRawValue.branchName),
      accountNumber: new FormControl(memberBankRawValue.accountNumber),
      ifsccode: new FormControl(memberBankRawValue.ifsccode),
      lastModified: new FormControl(memberBankRawValue.lastModified),
      lastModifiedBy: new FormControl(memberBankRawValue.lastModifiedBy),
      createdBy: new FormControl(memberBankRawValue.createdBy),
      createdOn: new FormControl(memberBankRawValue.createdOn),
      isDeleted: new FormControl(memberBankRawValue.isDeleted),
    });
  }

  getMemberBank(form: MemberBankFormGroup): IMemberBank | NewMemberBank {
    return this.convertMemberBankRawValueToMemberBank(form.getRawValue() as MemberBankFormRawValue | NewMemberBankFormRawValue);
  }

  resetForm(form: MemberBankFormGroup, memberBank: MemberBankFormGroupInput): void {
    const memberBankRawValue = this.convertMemberBankToMemberBankRawValue({ ...this.getFormDefaults(), ...memberBank });
    form.reset(
      {
        ...memberBankRawValue,
        id: { value: memberBankRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberBankFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertMemberBankRawValueToMemberBank(
    rawMemberBank: MemberBankFormRawValue | NewMemberBankFormRawValue
  ): IMemberBank | NewMemberBank {
    return {
      ...rawMemberBank,
      lastModified: dayjs(rawMemberBank.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMemberBank.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberBankToMemberBankRawValue(
    memberBank: IMemberBank | (Partial<NewMemberBank> & MemberBankFormDefaults)
  ): MemberBankFormRawValue | PartialWithRequiredKeyOf<NewMemberBankFormRawValue> {
    return {
      ...memberBank,
      lastModified: memberBank.lastModified ? memberBank.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: memberBank.createdOn ? memberBank.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

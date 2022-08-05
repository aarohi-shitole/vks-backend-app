import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMember, NewMember } from '../member.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMember for edit and NewMemberFormGroupInput for create.
 */
type MemberFormGroupInput = IMember | PartialWithRequiredKeyOf<NewMember>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMember | NewMember> = Omit<T, 'applicationDate' | 'lastModified' | 'createdOn'> & {
  applicationDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberFormRawValue = FormValueOf<IMember>;

type NewMemberFormRawValue = FormValueOf<NewMember>;

type MemberFormDefaults = Pick<NewMember, 'id' | 'applicationDate' | 'kmpStatus' | 'isactive' | 'lastModified' | 'createdOn'>;

type MemberFormGroupContent = {
  id: FormControl<MemberFormRawValue['id'] | NewMember['id']>;
  firstName: FormControl<MemberFormRawValue['firstName']>;
  middleName: FormControl<MemberFormRawValue['middleName']>;
  lastName: FormControl<MemberFormRawValue['lastName']>;
  memberUniqueId: FormControl<MemberFormRawValue['memberUniqueId']>;
  fatherName: FormControl<MemberFormRawValue['fatherName']>;
  motherName: FormControl<MemberFormRawValue['motherName']>;
  gender: FormControl<MemberFormRawValue['gender']>;
  dob: FormControl<MemberFormRawValue['dob']>;
  email: FormControl<MemberFormRawValue['email']>;
  mobileNo: FormControl<MemberFormRawValue['mobileNo']>;
  religion: FormControl<MemberFormRawValue['religion']>;
  category: FormControl<MemberFormRawValue['category']>;
  cast: FormControl<MemberFormRawValue['cast']>;
  aadharCard: FormControl<MemberFormRawValue['aadharCard']>;
  panCard: FormControl<MemberFormRawValue['panCard']>;
  rationCard: FormControl<MemberFormRawValue['rationCard']>;
  familyMemberCount: FormControl<MemberFormRawValue['familyMemberCount']>;
  occupation: FormControl<MemberFormRawValue['occupation']>;
  applicationDate: FormControl<MemberFormRawValue['applicationDate']>;
  status: FormControl<MemberFormRawValue['status']>;
  kmpStatus: FormControl<MemberFormRawValue['kmpStatus']>;
  boardResolutionNo: FormControl<MemberFormRawValue['boardResolutionNo']>;
  boardResolutionDate: FormControl<MemberFormRawValue['boardResolutionDate']>;
  loanStatus: FormControl<MemberFormRawValue['loanStatus']>;
  memberType: FormControl<MemberFormRawValue['memberType']>;
  isactive: FormControl<MemberFormRawValue['isactive']>;
  lastModified: FormControl<MemberFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberFormRawValue['createdBy']>;
  createdOn: FormControl<MemberFormRawValue['createdOn']>;
  memberBank: FormControl<MemberFormRawValue['memberBank']>;
  society: FormControl<MemberFormRawValue['society']>;
};

export type MemberFormGroup = FormGroup<MemberFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberFormService {
  createMemberFormGroup(member: MemberFormGroupInput = { id: null }): MemberFormGroup {
    const memberRawValue = this.convertMemberToMemberRawValue({
      ...this.getFormDefaults(),
      ...member,
    });
    return new FormGroup<MemberFormGroupContent>({
      id: new FormControl(
        { value: memberRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(memberRawValue.firstName),
      middleName: new FormControl(memberRawValue.middleName),
      lastName: new FormControl(memberRawValue.lastName),
      memberUniqueId: new FormControl(memberRawValue.memberUniqueId),
      fatherName: new FormControl(memberRawValue.fatherName),
      motherName: new FormControl(memberRawValue.motherName),
      gender: new FormControl(memberRawValue.gender),
      dob: new FormControl(memberRawValue.dob),
      email: new FormControl(memberRawValue.email),
      mobileNo: new FormControl(memberRawValue.mobileNo),
      religion: new FormControl(memberRawValue.religion),
      category: new FormControl(memberRawValue.category),
      cast: new FormControl(memberRawValue.cast),
      aadharCard: new FormControl(memberRawValue.aadharCard),
      panCard: new FormControl(memberRawValue.panCard),
      rationCard: new FormControl(memberRawValue.rationCard),
      familyMemberCount: new FormControl(memberRawValue.familyMemberCount),
      occupation: new FormControl(memberRawValue.occupation),
      applicationDate: new FormControl(memberRawValue.applicationDate),
      status: new FormControl(memberRawValue.status),
      kmpStatus: new FormControl(memberRawValue.kmpStatus),
      boardResolutionNo: new FormControl(memberRawValue.boardResolutionNo),
      boardResolutionDate: new FormControl(memberRawValue.boardResolutionDate),
      loanStatus: new FormControl(memberRawValue.loanStatus),
      memberType: new FormControl(memberRawValue.memberType),
      isactive: new FormControl(memberRawValue.isactive),
      lastModified: new FormControl(memberRawValue.lastModified),
      lastModifiedBy: new FormControl(memberRawValue.lastModifiedBy),
      createdBy: new FormControl(memberRawValue.createdBy),
      createdOn: new FormControl(memberRawValue.createdOn),
      memberBank: new FormControl(memberRawValue.memberBank),
      society: new FormControl(memberRawValue.society),
    });
  }

  getMember(form: MemberFormGroup): IMember | NewMember {
    return this.convertMemberRawValueToMember(form.getRawValue() as MemberFormRawValue | NewMemberFormRawValue);
  }

  resetForm(form: MemberFormGroup, member: MemberFormGroupInput): void {
    const memberRawValue = this.convertMemberToMemberRawValue({ ...this.getFormDefaults(), ...member });
    form.reset(
      {
        ...memberRawValue,
        id: { value: memberRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      applicationDate: currentTime,
      kmpStatus: false,
      isactive: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertMemberRawValueToMember(rawMember: MemberFormRawValue | NewMemberFormRawValue): IMember | NewMember {
    return {
      ...rawMember,
      applicationDate: dayjs(rawMember.applicationDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawMember.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMember.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberToMemberRawValue(
    member: IMember | (Partial<NewMember> & MemberFormDefaults)
  ): MemberFormRawValue | PartialWithRequiredKeyOf<NewMemberFormRawValue> {
    return {
      ...member,
      applicationDate: member.applicationDate ? member.applicationDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: member.lastModified ? member.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: member.createdOn ? member.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

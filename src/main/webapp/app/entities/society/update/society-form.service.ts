import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISociety, NewSociety } from '../society.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISociety for edit and NewSocietyFormGroupInput for create.
 */
type SocietyFormGroupInput = ISociety | PartialWithRequiredKeyOf<NewSociety>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISociety | NewSociety> = Omit<T, 'createdOn' | 'lastModified'> & {
  createdOn?: string | null;
  lastModified?: string | null;
};

type SocietyFormRawValue = FormValueOf<ISociety>;

type NewSocietyFormRawValue = FormValueOf<NewSociety>;

type SocietyFormDefaults = Pick<NewSociety, 'id' | 'createdOn' | 'isActivate' | 'lastModified'>;

type SocietyFormGroupContent = {
  id: FormControl<SocietyFormRawValue['id'] | NewSociety['id']>;
  societyName: FormControl<SocietyFormRawValue['societyName']>;
  address: FormControl<SocietyFormRawValue['address']>;
  village: FormControl<SocietyFormRawValue['village']>;
  tahsil: FormControl<SocietyFormRawValue['tahsil']>;
  state: FormControl<SocietyFormRawValue['state']>;
  district: FormControl<SocietyFormRawValue['district']>;
  logo: FormControl<SocietyFormRawValue['logo']>;
  logoContentType: FormControl<SocietyFormRawValue['logoContentType']>;
  registrationNumber: FormControl<SocietyFormRawValue['registrationNumber']>;
  gstinNumber: FormControl<SocietyFormRawValue['gstinNumber']>;
  panNumber: FormControl<SocietyFormRawValue['panNumber']>;
  tanNumber: FormControl<SocietyFormRawValue['tanNumber']>;
  phoneNumber: FormControl<SocietyFormRawValue['phoneNumber']>;
  emailAddress: FormControl<SocietyFormRawValue['emailAddress']>;
  pinCode: FormControl<SocietyFormRawValue['pinCode']>;
  createdOn: FormControl<SocietyFormRawValue['createdOn']>;
  createdBy: FormControl<SocietyFormRawValue['createdBy']>;
  description: FormControl<SocietyFormRawValue['description']>;
  isActivate: FormControl<SocietyFormRawValue['isActivate']>;
  lastModified: FormControl<SocietyFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyFormRawValue['lastModifiedBy']>;
  society: FormControl<SocietyFormRawValue['society']>;
};

export type SocietyFormGroup = FormGroup<SocietyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyFormService {
  createSocietyFormGroup(society: SocietyFormGroupInput = { id: null }): SocietyFormGroup {
    const societyRawValue = this.convertSocietyToSocietyRawValue({
      ...this.getFormDefaults(),
      ...society,
    });
    return new FormGroup<SocietyFormGroupContent>({
      id: new FormControl(
        { value: societyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      societyName: new FormControl(societyRawValue.societyName, {
        validators: [Validators.required],
      }),
      address: new FormControl(societyRawValue.address),
      village: new FormControl(societyRawValue.village),
      tahsil: new FormControl(societyRawValue.tahsil),
      state: new FormControl(societyRawValue.state),
      district: new FormControl(societyRawValue.district),
      logo: new FormControl(societyRawValue.logo),
      logoContentType: new FormControl(societyRawValue.logoContentType),
      registrationNumber: new FormControl(societyRawValue.registrationNumber),
      gstinNumber: new FormControl(societyRawValue.gstinNumber),
      panNumber: new FormControl(societyRawValue.panNumber),
      tanNumber: new FormControl(societyRawValue.tanNumber),
      phoneNumber: new FormControl(societyRawValue.phoneNumber),
      emailAddress: new FormControl(societyRawValue.emailAddress),
      pinCode: new FormControl(societyRawValue.pinCode),
      createdOn: new FormControl(societyRawValue.createdOn),
      createdBy: new FormControl(societyRawValue.createdBy),
      description: new FormControl(societyRawValue.description),
      isActivate: new FormControl(societyRawValue.isActivate),
      lastModified: new FormControl(societyRawValue.lastModified),
      lastModifiedBy: new FormControl(societyRawValue.lastModifiedBy),
      society: new FormControl(societyRawValue.society),
    });
  }

  getSociety(form: SocietyFormGroup): ISociety | NewSociety {
    return this.convertSocietyRawValueToSociety(form.getRawValue() as SocietyFormRawValue | NewSocietyFormRawValue);
  }

  resetForm(form: SocietyFormGroup, society: SocietyFormGroupInput): void {
    const societyRawValue = this.convertSocietyToSocietyRawValue({ ...this.getFormDefaults(), ...society });
    form.reset(
      {
        ...societyRawValue,
        id: { value: societyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
      isActivate: false,
      lastModified: currentTime,
    };
  }

  private convertSocietyRawValueToSociety(rawSociety: SocietyFormRawValue | NewSocietyFormRawValue): ISociety | NewSociety {
    return {
      ...rawSociety,
      createdOn: dayjs(rawSociety.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawSociety.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyToSocietyRawValue(
    society: ISociety | (Partial<NewSociety> & SocietyFormDefaults)
  ): SocietyFormRawValue | PartialWithRequiredKeyOf<NewSocietyFormRawValue> {
    return {
      ...society,
      createdOn: society.createdOn ? society.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: society.lastModified ? society.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

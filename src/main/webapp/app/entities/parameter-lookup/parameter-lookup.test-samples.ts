import dayjs from 'dayjs/esm';

import { ParameterType } from 'app/entities/enumerations/parameter-type.model';

import { IParameterLookup, NewParameterLookup } from './parameter-lookup.model';

export const sampleWithRequiredData: IParameterLookup = {
  id: 5221,
};

export const sampleWithPartialData: IParameterLookup = {
  id: 7285,
  displayValue: 'technologies compressing',
  lastModified: dayjs('2022-08-05T06:01'),
  lastModifiedBy: 'viral SCSI',
  isDeleted: true,
};

export const sampleWithFullData: IParameterLookup = {
  id: 22923,
  parameterType: ParameterType['RESOLUTION'],
  description: 'Fresh Cotton',
  displayValue: 'Soap',
  lastModified: dayjs('2022-08-04T14:27'),
  lastModifiedBy: 'Specialist Computer',
  createdBy: 'Berkshire',
  createdOn: dayjs('2022-08-04T18:57'),
  isDeleted: false,
};

export const sampleWithNewData: NewParameterLookup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

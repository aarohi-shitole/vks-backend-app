import dayjs from 'dayjs/esm';

import { ISociety, NewSociety } from './society.model';

export const sampleWithRequiredData: ISociety = {
  id: 88772,
  societyName: 'cutting-edge Diverse',
};

export const sampleWithPartialData: ISociety = {
  id: 40696,
  societyName: 'mesh Grenada Hawaii',
  address: 'flexibility eyeballs next-generation',
  village: 'Luxembourg',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  gstinNumber: 84454,
  panNumber: 5517,
  phoneNumber: 31030,
  createdOn: dayjs('2022-08-04T15:22'),
  isActivate: false,
  lastModified: dayjs('2022-08-05T06:04'),
};

export const sampleWithFullData: ISociety = {
  id: 19936,
  societyName: 'Group plum Optimization',
  address: 'interface synthesizing Checking',
  village: 'encryption SCSI Coordinator',
  tahsil: 'Islands redundant',
  state: 'web-readiness',
  district: 'enable',
  logo: '../fake-data/blob/hipster.png',
  logoContentType: 'unknown',
  registrationNumber: 28669,
  gstinNumber: 47996,
  panNumber: 51207,
  tanNumber: 5155,
  phoneNumber: 53143,
  emailAddress: 'projection',
  pinCode: 51942,
  createdOn: dayjs('2022-08-04T17:23'),
  createdBy: 'generating Plastic',
  description: 'Cambridgeshire orange',
  isActivate: false,
  lastModified: dayjs('2022-08-04T16:07'),
  lastModifiedBy: 'algorithm markets Wooden',
};

export const sampleWithNewData: NewSociety = {
  societyName: 'Books Pound',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

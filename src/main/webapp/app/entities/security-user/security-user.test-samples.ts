import dayjs from 'dayjs/esm';

import { ISecurityUser, NewSecurityUser } from './security-user.model';

export const sampleWithRequiredData: ISecurityUser = {
  id: 67981,
  username: 'panel',
  passwordHash: 'Massachusetts',
};

export const sampleWithPartialData: ISecurityUser = {
  id: 693,
  firstName: 'Ivy',
  lastName: 'Weber',
  designation: 'Belize Legacy',
  username: 'Intranet application',
  passwordHash: 'Producer',
  description: 'Response AI',
  department: 'multi-byte',
  resetDate: dayjs('2022-08-05T07:37'),
  mobileNo: 'Loan',
  createdBy: 'Electronics',
};

export const sampleWithFullData: ISecurityUser = {
  id: 80516,
  firstName: 'Bart',
  lastName: 'Heidenreich',
  designation: 'Shoes SAS',
  username: 'Plastic New copy',
  passwordHash: 'Keyboard Kids',
  email: 'Skylar.Crooks@gmail.com',
  description: 'SAS',
  department: 'override',
  imageUrl: 'Litas Versatile Avon',
  activated: true,
  langKey: 'Regional object-oriented encryption',
  activationKey: 'Station synthesizing',
  resetKey: 'invoice relationships 24/7',
  resetDate: dayjs('2022-08-05T01:11'),
  mobileNo: 'Lek',
  createdBy: 'Senior Tanzanian',
  createdOn: dayjs('2022-08-04T09:58'),
};

export const sampleWithNewData: NewSecurityUser = {
  username: 'Ergonomic Program',
  passwordHash: 'redundant',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

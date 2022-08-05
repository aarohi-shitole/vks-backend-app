import dayjs from 'dayjs/esm';

import { ISecurityRole, NewSecurityRole } from './security-role.model';

export const sampleWithRequiredData: ISecurityRole = {
  id: 76583,
  roleName: 'Plastic compressing New',
};

export const sampleWithPartialData: ISecurityRole = {
  id: 72100,
  roleName: 'microchip',
  lastModified: dayjs('2022-08-04T15:02'),
  lastModifiedBy: 'invoice Wisconsin drive',
};

export const sampleWithFullData: ISecurityRole = {
  id: 99223,
  roleName: 'Awesome navigating',
  description: 'Global salmon invoice',
  lastModified: dayjs('2022-08-05T01:25'),
  lastModifiedBy: 'USB improvement',
};

export const sampleWithNewData: NewSecurityRole = {
  roleName: 'Account dynamic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import dayjs from 'dayjs/esm';

import { INominee, NewNominee } from './nominee.model';

export const sampleWithRequiredData: INominee = {
  id: 87924,
};

export const sampleWithPartialData: INominee = {
  id: 47030,
  middleName: 'Car digital Shoes',
  motherName: 'neural Cordoba',
  dob: dayjs('2022-08-04'),
  lastModified: dayjs('2022-08-05T08:14'),
  lastModifiedBy: 'Berkshire',
  isDeleted: false,
};

export const sampleWithFullData: INominee = {
  id: 9937,
  firstName: 'Luz',
  middleName: 'implementation',
  lastName: "O'Hara",
  fatherName: 'Islands zero',
  motherName: 'cross-platform',
  guardianName: 'Steel',
  gender: 'payment',
  dob: dayjs('2022-08-04'),
  aadharCard: 'Orchestrator Nepalese',
  nomineeDeclareDate: dayjs('2022-08-04T15:35'),
  relation: 'deliverables Won Generic',
  permanentAddr: 'turquoise',
  lastModified: dayjs('2022-08-04T23:41'),
  lastModifiedBy: 'Concrete archive',
  createdBy: 'invoice superstructure',
  createdOn: dayjs('2022-08-05T07:13'),
  isDeleted: false,
};

export const sampleWithNewData: NewNominee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

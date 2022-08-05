import dayjs from 'dayjs/esm';

import { Gender } from 'app/entities/enumerations/gender.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

import { IMember, NewMember } from './member.model';

export const sampleWithRequiredData: IMember = {
  id: 27280,
};

export const sampleWithPartialData: IMember = {
  id: 64165,
  middleName: 'Awesome Utah',
  memberUniqueId: 'COM',
  dob: dayjs('2022-08-05'),
  religion: 'neural e-tailers',
  cast: 'Oregon',
  panCard: 'Soft Dollar',
  occupation: 'haptic Garden',
  status: Status['DOCUMENT_VERIFIED'],
  kmpStatus: true,
  boardResolutionDate: dayjs('2022-08-05'),
  loanStatus: LoanStatus['APPLIED'],
  memberType: 'programming XML USB',
  isactive: true,
  lastModifiedBy: 'Architect',
  createdBy: 'Mobility',
  createdOn: dayjs('2022-08-04T13:31'),
};

export const sampleWithFullData: IMember = {
  id: 99547,
  firstName: 'Ola',
  middleName: 'Trail quantifying',
  lastName: 'Bahringer',
  memberUniqueId: 'success',
  fatherName: 'holistic',
  motherName: 'Incredible plum Refined',
  gender: Gender['FEMALE'],
  dob: dayjs('2022-08-04'),
  email: 'Narciso.Welch17@yahoo.com',
  mobileNo: 'Bike National adapter',
  religion: 'Massachusetts',
  category: 'content Soap',
  cast: 'Automotive',
  aadharCard: 'implement index',
  panCard: 'Micronesia parsing Jewelery',
  rationCard: 'Persistent Lead Investor',
  familyMemberCount: 93477,
  occupation: 'Herzegovina Cambridgeshire architect',
  applicationDate: dayjs('2022-08-04T10:44'),
  status: Status['LOAN_ACTIVE'],
  kmpStatus: true,
  boardResolutionNo: 'orchestration ROI matrices',
  boardResolutionDate: dayjs('2022-08-05'),
  loanStatus: LoanStatus['APPLIED'],
  memberType: 'connecting violet',
  isactive: false,
  lastModified: dayjs('2022-08-04T15:55'),
  lastModifiedBy: 'Outdoors transmitting',
  createdBy: 'Michigan Administrator Kazakhstan',
  createdOn: dayjs('2022-08-04T11:33'),
};

export const sampleWithNewData: NewMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

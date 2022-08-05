import dayjs from 'dayjs/esm';
import { IMemberBank } from 'app/entities/member-bank/member-bank.model';
import { ISociety } from 'app/entities/society/society.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

export interface IMember {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  memberUniqueId?: string | null;
  fatherName?: string | null;
  motherName?: string | null;
  gender?: Gender | null;
  dob?: dayjs.Dayjs | null;
  email?: string | null;
  mobileNo?: string | null;
  religion?: string | null;
  category?: string | null;
  cast?: string | null;
  aadharCard?: string | null;
  panCard?: string | null;
  rationCard?: string | null;
  familyMemberCount?: number | null;
  occupation?: string | null;
  applicationDate?: dayjs.Dayjs | null;
  status?: Status | null;
  kmpStatus?: boolean | null;
  boardResolutionNo?: string | null;
  boardResolutionDate?: dayjs.Dayjs | null;
  loanStatus?: LoanStatus | null;
  memberType?: string | null;
  isactive?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  memberBank?: Pick<IMemberBank, 'id'> | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewMember = Omit<IMember, 'id'> & { id: null };

import dayjs from 'dayjs/esm';

export interface IMemberBank {
  id: number;
  bankName?: string | null;
  branchName?: string | null;
  accountNumber?: number | null;
  ifsccode?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
}

export type NewMemberBank = Omit<IMemberBank, 'id'> & { id: null };

import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';

export interface INominee {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  fatherName?: string | null;
  motherName?: string | null;
  guardianName?: string | null;
  gender?: string | null;
  dob?: dayjs.Dayjs | null;
  aadharCard?: string | null;
  nomineeDeclareDate?: dayjs.Dayjs | null;
  relation?: string | null;
  permanentAddr?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewNominee = Omit<INominee, 'id'> & { id: null };

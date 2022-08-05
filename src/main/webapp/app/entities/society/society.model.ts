import dayjs from 'dayjs/esm';

export interface ISociety {
  id: number;
  societyName?: string | null;
  address?: string | null;
  village?: string | null;
  tahsil?: string | null;
  state?: string | null;
  district?: string | null;
  logo?: string | null;
  logoContentType?: string | null;
  registrationNumber?: number | null;
  gstinNumber?: number | null;
  panNumber?: number | null;
  tanNumber?: number | null;
  phoneNumber?: number | null;
  emailAddress?: string | null;
  pinCode?: number | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  description?: string | null;
  isActivate?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewSociety = Omit<ISociety, 'id'> & { id: null };
